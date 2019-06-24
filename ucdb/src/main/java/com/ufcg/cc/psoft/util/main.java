package com.ufcg.cc.psoft.util;

import java.util.HashSet;

public class main {

    public static void main(String[] args) {
        HashSet<Integer> a = new HashSet<>();
        a.add(1); a.add(2); a.add(3);
        System.out.println(a);
        a.remove(5);
        System.out.println(a);
        a.remove(2);
        System.out.println(a);

        int size = 0;
        for (Integer b : a) {
            size++;
        }

        System.out.println("hello b i t c h");
    }
}
