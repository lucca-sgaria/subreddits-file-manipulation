package br.com.ucs.subreddits.manipulation.service;

import java.util.HashMap;
import java.util.List;

public class HashService {

    private HashMap<Integer, List<Integer>> map = new HashMap<>();


    public int hash(int ano,int mes) {
        int inteiro = mes + ano;
        return inteiro/2019;
    }
}
