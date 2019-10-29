package br.com.ucs.subreddits.manipulation;

import br.com.ucs.subreddits.manipulation.constants.Path;
import br.com.ucs.subreddits.manipulation.service.MainFileDateIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileIdIndexedService;
import br.com.ucs.subreddits.manipulation.service.MainFileServiceBean;
import br.com.ucs.subreddits.manipulation.service.RawDataFileServiceBean;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws IOException {
        RawDataFileServiceBean rawService = new RawDataFileServiceBean();
        rawService.createAndSetRawDataFile(Path.RAW_FILE);
        rawService.updateFileSubreddits();
//        rawService.printRawComplete();
//        rawService.printSubredditList();

        MainFileServiceBean mainService = new MainFileServiceBean();
        mainService.createAndPopulateFile(Path.MAIN_FILE, rawService.getRawDataFile().getSubredditList());
//        mainService.printMainFileComplete();
        System.out.println(mainService.binarySearchById("10"));

        MainFileIdIndexedService idIndexedService = new MainFileIdIndexedService(mainService.getMainFile());
        idIndexedService.createAndPopulateIdFile(Path.INDEXED_ID_FILE);
//        String s = idIndexedService.binarySearchById("240");
//
//        Long aLong = Long.valueOf(s);
//        System.out.println(aLong);
//
//        mainService.findByAdress(aLong.intValue());

        MainFileDateIndexedService mainFileDateIndexedService = new MainFileDateIndexedService(mainService.getMainFile());
        mainFileDateIndexedService.createAndPopulateDateFile(Path.INDEXED_DATE_FILE);

    }
}
