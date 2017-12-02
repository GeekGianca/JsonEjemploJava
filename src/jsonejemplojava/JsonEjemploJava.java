/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonejemplojava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author VIVE DIGITAL
 */
public class JsonEjemploJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se declaran los objetos de la Clase
        //Para su manejo
        JSONObject json;
        JSONArray arrayMaterias = new JSONArray();
        JSONObject notation = new JSONObject();
        //Se crea una instancia de la clase Scanner
        Scanner in = new Scanner(System.in);
        //Se capturan los datos
        System.out.println("Digite el id del Objeto");
        int idC = in.nextInt();
        notation.put("ID-Database", idC);
        System.out.println("Digite la cantidad de Materias");
        int cantidadM = in.nextInt();
        for (int i = 0; i < cantidadM; i++) {
            System.out.println("Digite el nombre de la Materia " + (i + 1));
            String materia = in.next();
            System.out.println("Digite el codigo de la materia " + (i + 1));
            int codigo = in.nextInt();
            //Se agregan los datos capturados al objeto JSON
            json = new JSONObject();
            json.put("IDMateria", codigo);
            json.put("NMateria", materia);
            //Se guarda como un array de objetos en un JSON
            arrayMaterias.add(json);
        }
        System.out.println(arrayMaterias.toJSONString());
        //Luego ese ArrayJson se guarda como Objeto de la estructura JSON
        notation.put("Materias", arrayMaterias);
        //Se crea la misma sentencia
        arrayMaterias = new JSONArray();
        for (int i = 0; i < cantidadM; i++) {
            System.out.println("Ingrese ID Curso " + (i + 1));
            int id = in.nextInt();
            json = new JSONObject();
            json.put("ID-", id);
            arrayMaterias.add(json);
        }
        notation.put("ID-CLASES", arrayMaterias);
        System.out.println("Primer Nombre del Estudiante");
        String nombre = in.next();
        System.out.println("Segundo Nombre del Estudiante");
        String nombreL = in.next();
        System.out.println("Apellido del Estudiante");
        String apellido = in.next();
        notation.put("Nombre", nombre + " " + nombreL + " " + apellido);
        System.out.println("Tiene profesor?");
        boolean profesor = true;
        notation.put("Profesor", profesor);
        System.out.println("Datos JSON: ");
        System.out.println(notation);
        //Se captura el JSON y se envia a un archivo
        File file = new File("JsonObjectNotation.json");
        BufferedWriter writer;
        try {
            if (file.exists()) {
                writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(notation.toJSONString());
                writer.newLine();
            } else {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(notation.toJSONString());
                writer.newLine();
            }
            writer.close();
            System.out.println("Save File Complete");
//            PrintWriter writer = new PrintWriter(file);
//            writer.print(notation);
        } catch (IOException ex) {
            System.out.println("Error en la escritura");
        }
        try {
            //Today Json Parser
            //Read files with Scanner Json
            //Se leen datos de entrada por medio del Scanner
            in = new Scanner(file);
            //Se crea una instancia del objeto StringBuilder
            StringBuilder jsonIn = new StringBuilder();
            //Mientras que el objeto in de Scanner
            //Tenga datos se van asignando al objeto 
            //De StringBuilder
            while (in.hasNextLine()) {
                jsonIn.append(in.nextLine() + "\n");
            }
            System.out.println(jsonIn);
            //Parseo de Datos a JSON
            //Se crea una instancia del Objeto JSONParser
            JSONParser parser = new JSONParser();
            JSONObject objRoot = (JSONObject) parser.parse(jsonIn.toString());
            System.out.printf("EL nombre del estudiante es %s", objRoot.get("Nombre").toString());

            JSONArray cursos = (JSONArray) objRoot.get("Materias");
            for (int i = 0; i < cursos.size(); i++) {
                JSONObject cursosEn = (JSONObject) cursos.get(i);
                long id = (long) cursosEn.get("IDMateria");
                String name = (String) cursosEn.get("NMateria");
                System.out.printf("Cursos %s; ID- Cursos %d\n", name, id);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Exception Type: " + ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(JsonEjemploJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JsonEjemploJava.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
