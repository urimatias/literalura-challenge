package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autores;
import com.aluracursos.literalura.model.DatosResults;
import com.aluracursos.literalura.model.Results;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private LibroRepository repository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository= autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            try{
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (Exception InputMismatchException){
                System.out.println("Opción invalida");
            }


            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    try{
                        var opcion5 = -1;
                        while (opcion5 != 0) {
                            var menu5 = """
                                Elija el idioma que desea listar:
                                1 - Español
                                2 - Ingles
                                3 - Frances
                                0 - Regresar al menú principal
                                """;
                            System.out.println(menu5);
                            opcion5 = teclado.nextInt();
                            teclado.nextLine();
                            switch (opcion5){
                                case 1:
                                    ListarlibrosPorIdioma("es");
                                    break;
                                case 2:
                                    ListarlibrosPorIdioma("en");
                                    break;
                                case 3:
                                    ListarlibrosPorIdioma("fr");
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Opción inválida");
                            }
                        }
                    }catch (Exception InputMismatchException){
                        System.out.println("Opción invalida");
                    }
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }
    }

    //****************** METODO BUSCAR LIBRO POR TITULO*************************************************
    //******************           CASE 1              *************************************************
    private void buscarLibroTitulo() {
        //                BUSCADOR DE LIBRO Y MANEJO DE JSON
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE  + nombreLibro.replace(" ", "%20"));
        System.out.println(json);
        ConvierteDatos conversor = new ConvierteDatos();
        var datos = conversor.obtenerDatos(json, DatosResults.class);



        //                  FILTRO PARA SABER SI HAY LIBRO O NO
        if (datos.resultados().isEmpty()){
            System.out.println("Libro no encontrado");
        }else{

            //                   Guardado de informacion
            Results results = new Results(datos);
            Autores autores = new Autores(datos);
            try {
                repository.save(results);
                autorRepository.save(autores);

            }catch (Exception InvalidDataAccessApiUsageException){
                System.out.println("El siguiente libro ya fue registrado: ");
            }

            //               IMPRESION DE INFORMACION
            System.out.println("---------------------\n" +
                    "Nombre de libro:" + datos.resultados().get(0).titulo() +
                    "\nNombre del autor: " + datos.resultados().get(0).autores().get(0).nombreAutor() +
                    "\nIdiomas: " + datos.resultados().get(0).idiomas().toString() +
                    "\nNúmero de descargas: " + datos.resultados().get(0).numeroDeDesacargas() +
                    "\n---------------------\n");
        }
    }

    //**************************METODO PARA MOSTRAR LIBROS**************************************
    //**************************         CASE 2           **************************************
   private void mostrarLibrosBuscados(){
       List<Results> resultsList = repository.findAll();
       resultsList.stream()
               .toList()
               .forEach(s ->
                       System.out.println("\n---------Libro---------\n" +
                               "\n Titulo de libro: " + s.getTitulo() +
                               "\n Autor: " + s.getAutores() +
                               "\n Idioma: " + s.getIdiomas() +
                               "\n Numero de descargas: " + s.getNumeroDeDesacargas() +
                               "\n-----------------------\n"));
   }

//***************************METODO PARA MOSTRAR LOS AUTORES REGISTRADOS***************************
    //***********************             CASE 3                        ***************************
    private void listarAutoresRegistrados() {

        List<Autores> resultsAutoresList = autorRepository.findAll();
        resultsAutoresList.stream()
                .toList()
                .forEach(s ->
                        System.out.println("\n---------Autor---------\n" +
                                "\n Autor: " + s.getNombreAutor() +
                                "\n Fecha de nacimiento: " + s.getAnoNacimiento() +
                                "\n Fecha de fallecimiento: " + s.getAnoMuerte() +
                                "\n-----------------------\n"));
    }

//***************************METODO PARA MOSTRAR LOS AUTORES VIVOS***************************
    //***********************             CASE 4                        ***************************
    private void listarAutoresVivos() {
        System.out.println("Ingrese el año del que desea consultar a los autores: ");
        try {
            int opcionFecha = teclado.nextInt();
            teclado.nextLine();
            if (opcionFecha > 2024){
                System.out.println("Opción invalida");
            }else{
                List<Autores> autoresVivos = autorRepository.autoresVivosEnLaFecha(opcionFecha);
                if (autoresVivos.isEmpty()){
                    System.out.println("No habia autores vivos en ese año");
                }else{
                    System.out.println("*****************Autores vivos en el año " + opcionFecha + "*****************");
                    autoresVivos.forEach(s ->
                            System.out.println("\n---------Autor---------\n" +
                                    "\n Autor: " + s.getNombreAutor() + "\n"));
                }
            }
        }catch (Exception InputMismatchException){
            System.out.println("Opción invalida");
        }

    }



    //***************************METODO PARA MOSTRAR LOS Idiomas***************************
    //***********************             CASE 5                        ***************************
    private void ListarlibrosPorIdioma(String idioma) {
        List<Results> resultsList = repository.findAll();
        resultsList.stream()
                .toList()
                .stream().filter(s -> s.getIdiomas().contains(idioma))
                .forEach(s ->
                        System.out.println("\n---------Libro---------\n" +
                                "\n Titulo de libro: " + s.getTitulo() +
                                "\n Autor: " + s.getAutores() +
                                "\n Idioma: " + s.getIdiomas() +
                                "\n Numero de descargas: " + s.getNumeroDeDesacargas() +
                                "\n-----------------------\n"));


    }

}
