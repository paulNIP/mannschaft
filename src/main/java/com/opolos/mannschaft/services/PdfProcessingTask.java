package com.opolos.mannschaft.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.nio.file.*;
import java.io.IOException;


@Component
public class PdfProcessingTask {

    @Autowired
    private PdfService pdfService;

    @Value("${pdf.folder.path}")
    private String folderPath;

    static String[] lines;

    @Scheduled(cron = "0 * * * * ?") // Every Minute
    public void processPdfFiles() {
        // Define the directory path
        Path directoryPath = Paths.get(folderPath);

        // Use a try-with-resources statement to ensure the directory stream is closed
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            // Loop through the directory
            for (Path entry : stream) {
                // Check if the entry is a directory
                if (Files.isDirectory(entry)) {
                    // Multiple Directory Print the directory path

                    // Skip default folders
                    if(entry.toAbsolutePath().toString().contains("snap") ||
                       entry.toAbsolutePath().toString().contains("smap") ||
                       entry.toAbsolutePath().toString().contains("smap_version") ||
                       entry.toAbsolutePath().toString().contains("Sync") ||
                       entry.toAbsolutePath().toString().contains("mannschaft") ||
                       entry.toAbsolutePath().toString().contains("os_smap_23_05_18_893.tgz") ||
                       entry.toAbsolutePath().toString().contains(".") 
                    
                    ){

                    }else{
                        
                        File folder = new File(entry.toAbsolutePath().toString()+"/Reports");
                        if (folder.exists() && folder.isDirectory()) {
                            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

                            if (files != null) {
                                for (File file : files) {
                                    try {
                                        
                                        //check if file name doesnot contain list
                                        if(file.getName().contains("list")){

                                        }else{

                                            //store file path as file name
                                            //if report doesnot exist in our database
                                            if(pdfService.checkReportExistence(file.getPath())==false){
                                                //read Pdf file and insert into database
                                                processPdf(file);

                                            }else{
                                                System.out.println("Report Already inserted");
                                            }  

                                        }
                                         

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }else{
                            System.out.println("Folder Doesnt exist Yooooooooo--"+ entry.toAbsolutePath().toString()+"/Reports");
                        }

                    }

                    


                 }
            }
        } catch (IOException | DirectoryIteratorException e) {
            // Handle any exceptions that occur
            System.err.println("Error reading directory: " + e.getMessage());
        }

        
    }

    private void processPdf(File file) throws IOException {
        

        try (PDDocument document = PDDocument.load(file)) {
            //insert report information

            String filename =  file.getName();
            String filepath =  file.getPath();

            System.out.println(filepath);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            lines = text.split(System.getProperty("line.separator"));
            // System.out.println("Text in PDF: " + text);
            // Further processing of the text

            String clientId =lines[getLineNumberWithFilter("Auftraggeber (Kunde):")].split(":  ")[1];

            // client Details
            String client=lines[2];
            String street=lines[3];
            String address=lines[4];

            String[] vowels = {"mod","user"};
		
	        Set<String> vowelsSet = new HashSet <> (Arrays.asList(vowels));

            boolean emailExistence = pdfService.checkEmailExistence(client.replaceAll(" ", "").toLowerCase()+"@opolos.com");
            boolean clientExistence= pdfService.checkClientExistence(client);
            
            //check if client doesnot exist in user database
            // if(emailExistence && clientExistence){
            //     pdfService.insertNewUser(
            //         client,
            //         client,
            //         client.replaceAll(" ", "").toLowerCase()+"@opolos.com",
            //         "1234",
            //         clientId,
            //         street,
            //         address,
            //         null,
            //         null,
            //         null,
            //         vowelsSet,
            //         "1"
                
            //     );


            // }


                //application type,identification number,test_according_to

                String application_type=lines[getLineNumberWithFilter("Geräteart: ")].split(":  ")[1].trim().split(" ")[0];
                String identification_number=lines[getLineNumberWithFilter("Ident-. Nr.: ")].split(": ")[1].trim().split(" ")[0];
                
                

                String test_according_to=lines[14];

                
                //manufucturer,department,measuring length,  profile
                String manufucturer = lines[getLineNumberWithFilter("Hersteller: ")].split("Hersteller:")[1].split("Seriennummer:")[0];
                String department = lines[getLineNumberWithFilter("Abteilung: ")].split("Abteilung:")[1].split("Gerätetyp")[0];
                



                String measuring_length = lines[getLineNumberWithFilter("Schutzleiterlänge (m): ")].split(":  ")[1];
                String profile = lines[getLineNumberWithFilter("Messprofil: ")].split(":  ")[1].trim().replaceAll("  Prüfdatum", "");

               

                //manufucturer,department,measuring length,  profile,type
                String type_number = lines[getLineNumberWithFilter("Typ:")];
                String serial_number = lines[getLineNumberWithFilter("Seriennummer:")];
                String device_type = lines[getLineNumberWithFilter("Gerätetyp: ")];
                String cross_section = lines[getLineNumberWithFilter("Querschnitt (qmm): ")];
                String test_date = lines[getLineNumberWithFilter("Prüfdatum: ")];




                String last = test_date.substring(test_date.lastIndexOf(' ') + 1);

                String serialNumber=serial_number.substring(serial_number.lastIndexOf(':') + 1);
                String deviceType=device_type.substring(device_type.lastIndexOf(':') + 1);
                String crossSection=cross_section.substring(cross_section.lastIndexOf(':') + 1);

                String testDate=dateConvert(last);
                String next = lines[getLineNumberWithFilter("Nächster Prüftermin")];
                System.out.println(next);
                String next_test_date = dateConvert(next.substring(next.lastIndexOf(' ') + 1));

                String num =type_number.substring(17);
                String examiner = lines[getLineNumberWithFilter("Prüfer: ")].split(": ")[1].split(" ")[0];


                //  pdfService.insertNewReport(
                //         Year.now().toString(),
                //         client,
                //         clientId,
                //         application_type,
                //         identification_number,
                //         test_according_to,
                //         manufucturer,
                //         department,
                //         measuring_length,
                //         profile,
                //         filepath,
                //         filename,
                //         num,
                //         serialNumber,
                //         deviceType,
                //         crossSection,
                //         testDate,
                //         next_test_date,
                //         examiner,
                //         true  
                //  );
            
            
            
        }
    }


    // searches all lines for first line index containing `filter`
    static int getLineNumberWithFilter(String filter) {
        int n = 0;
        for(String line : lines) {
            if(line.indexOf(filter) != -1) {
                return n;
            }
            n++;
        }
        return -1;
    }

    // returns a full String line by number n
    static String getLine(int n) {
        return lines[n];
    }

    static String dateConvert(String inputDateString){

        // Define the input and output date formats
        DateTimeFormatter inputFormat = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter outputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        
        // Parse the input date string to a DateTime object
        DateTime dateTime = inputFormat.parseDateTime(inputDateString);
        
        // Format the DateTime object to the desired output format
        String outputDateString = outputFormat.print(dateTime);

        return outputDateString;
    }


    
}
