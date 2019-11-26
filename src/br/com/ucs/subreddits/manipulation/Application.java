package br.com.ucs.subreddits.manipulation;

import br.com.ucs.subreddits.manipulation.constants.Path;
import br.com.ucs.subreddits.manipulation.model.Subreddit;
import br.com.ucs.subreddits.manipulation.service.HashService;
import br.com.ucs.subreddits.manipulation.service.MainFileDateIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileIdIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileServiceBean;
import br.com.ucs.subreddits.manipulation.service.RawDataFileServiceBean;
import br.com.ucs.subreddits.mongojson.service.JsonGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {
    RawDataFileServiceBean rawService;
    MainFileServiceBean mainService;
    MainFileIdIndexedService idIndexedService;
    MainFileDateIndexedService mainFileDateIndexedService;

    public void startApplication() {
        System.out.println("Iniciando aplicação...");
        rawService = new RawDataFileServiceBean();
        rawService.createAndSetRawDataFile(Path.RAW_FILE_PARTIAL);
        rawService.updateFileSubreddits();
        System.out.println("Finalizado lista de registro buscadas no arquivo de dados brutos...");
        System.out.println("====================================================================");

        System.out.println("Iniciando inserção dos registros no arquivo de dados sequenciais...");
        mainService = new MainFileServiceBean();
        mainService.createAndPopulateFile(Path.MAIN_FILE, rawService.getRawDataFile().getSubredditList());
        System.out.println("Finalizado lista de registros no arquivo sequencial principal...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação e inserção do arquivo de índice primário baseados no campo id...");
        idIndexedService = new MainFileIdIndexedService(mainService.getMainFile());
        idIndexedService.createAndPopulateIdFile(Path.INDEXED_ID_FILE);
        System.out.println("Finalizado arquivo de índices baseados no campo id...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação e inserção do arquivo de índice secundáros baseados no campo data...");
        mainFileDateIndexedService = new MainFileDateIndexedService(mainService.getMainFile());
        mainFileDateIndexedService.createAndPopulateDateFile(Path.INDEXED_DATE_FILE);
        System.out.println("Finalizado criação e inserção do arquivo de índice primário baseados no campo data...");

        System.out.println("====================================================================");
        System.out.println("Iniciando criação de índices de hash de datas...");
        HashService hashService = new HashService();
        hashService.createMap(mainFileDateIndexedService.getIndexedFile());
        System.out.println("Finalizado criação de índices de hash de datas...");
        
        JsonGenerator jsonGenerator = new JsonGenerator();
        List<Subreddit> subredditList = rawService.getRawDataFile().getSubredditList();
        rawService.printSubredditList();
        List<String> listJson = jsonGenerator.getJson(subredditList);
//        System.out.println("Stringa");
//        System.out.println(json);

        
//        System.out.println("printar mapa");
//        hashService.printMap();
    }


    public int menu() {
        System.out.println("====================================================================");
        System.out.println("Bem-vindo ao menu \n Opções");
        System.out.println("1. Realizar pesquisa no arquivo principal sequencial. ");
        System.out.println("2. Realizar pesquisa no arquivo principal sequencial-indexado através do índice do campo id. ");
        System.out.println("3. Realizar pesquisa no arquivo principal sequencial-indexado através do índice do campo data. ");

        System.out.println("-1.Exit");

        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            System.out.println("Informe : ");
            String next = scanner1.next().trim();
            switch (next) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "-1":
                    return -1;
                default:
            }
        }
    }

    public boolean switchApplication(int choice) {
        switch (choice) {
            case 1:
                binarySearchId(1);
                break;
            case 2:
                binarySearchId(2);
                break;
            case 3:
                binarySearchByDate();
                break;
            case -1:
                return false;
            default:
        }

        return true;
    }

    @SuppressWarnings("Duplicates")
    private void binarySearchByDate() {
        Scanner scanner1 = new Scanner(System.in);

        while (true) {
            System.out.println("Informe o ano : ");
            String ano = scanner1.next().trim();

            int id = 0;
            try {
                id = Integer.parseInt(ano);
            } catch (NumberFormatException ex) {
                continue;
            }

            System.out.println("Informe o mes : ");
            String mesString = scanner1.next().trim();

            int mes = 0;
            try {
                mes = Integer.parseInt(mesString);
            } catch (NumberFormatException ex) {
                continue;
            }

            System.out.println("Informe o dia : ");
            String diaString = scanner1.next().trim();

            int dia = 0;
            try {
                dia = Integer.parseInt(diaString);
            } catch (NumberFormatException ex) {
                continue;
            }

            Calendar c = Calendar.getInstance();
            c.set(id, mes - 1, dia, 0, 0);

            Date time = c.getTime();
            System.out.println("date " + time.toString());
            String address = mainFileDateIndexedService.binarySearchByData(time);

            System.out.println("address " + address);

            String[] split = address.split(";");
            if (split.length == 2) {
                int pos = Integer.valueOf(split[0]);
                int finalPos = Integer.valueOf(split[1]);

                mainService.printMainFileInterval(pos, finalPos);
            }


        }
    }


    private void binarySearchId(int mode) {
        Scanner scanner1 = new Scanner(System.in);

        int id = 0;
        while (true) {
            System.out.println("Informe um inteiro : ");
            String next = scanner1.next().trim();

            id = 0;
            try {
                id = Integer.parseInt(next);
                break;
            } catch (NumberFormatException ex) {
            }
        }

        String key = String.valueOf(id);
        if (mode == 1) {
            System.out.println(mainService.binarySearchById(key));
        } else if (mode == 2) {
            try {
                String address = idIndexedService.binarySearchById(key);
                int position = Integer.parseInt(address);
                mainService.findByAdress(position);
            } catch (Exception e) {
            }
        }


    }


}
