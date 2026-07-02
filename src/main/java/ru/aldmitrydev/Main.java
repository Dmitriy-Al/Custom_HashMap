package ru.aldmitrydev;

public class Main {
    public static void main(String[] args) {

        HashMap<Integer, String> map = new HashMap<>();
        map.put(null, "NULL");

        for (int i = 0; i < 30; i++) {
            map.put(i, "value " + i);
        }

        System.out.println("map.size() = " + map.size());
        map.printInfo();
        System.out.println();

        System.out.println("get 20 = " + map.get(20));
        System.out.println("put 20, return " + map.put(20, "200"));
        System.out.println("map.remove 0 = " + map.remove(0));
        System.out.println("map.remove 1 = " + map.remove(1));
        System.out.println("map.remove 2 = " + map.remove(2));
        System.out.println("map.remove 13 = " + map.remove(13));
        System.out.println("map.remove 20 = " + map.remove(20));
        System.out.println("map.remove 20 = " + map.remove(20));
        System.out.println("map.remove null = " + map.remove(null));
        System.out.println("map.remove -7 = " + map.remove(-7));
        System.out.println("get 20 = " + map.get(20));

        map.printInfo();
        System.out.println();
        System.out.println("map.size() = " + map.size());
        System.out.println("map.get 40 = " + map.get(40));

    }
}
        /*
        HashMap<Integer, Integer> map = new HashMap<>();
        // map.put(null, "NULL");

        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
        //  System.out.println("map.amount = " + map.amount);


        System.out.println("map.size() = " + map.size());
        map.printInfo();
        System.out.println();

        // System.out.println("get = " + map.get("" + 25));
        System.out.println("map.remove 25 = " + map.remove(25));
        System.out.println("map.remove 15 = " + map.remove(15));
        System.out.println("map.remove 5 = " + map.remove(5));
        System.out.println("map.remove 13 = " + map.remove(13));
        System.out.println("map.remove 8 = " + map.remove(8));
        System.out.println("map.remove 9 = " + map.remove(9));


        map.printInfo();
        System.out.println("map.size() = " + map.size());
         */