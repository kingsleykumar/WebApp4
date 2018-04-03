package com.sb.services.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kingsley Kumar on 26/02/2017.
 */
public class FlattenList {


    public static List<Object> flattenList(List<Object> list) {

        List<Object> newList = new ArrayList();

        list.forEach(e -> {
            if (!(e instanceof List)) {

                newList.add(e);
            } else {

                newList.addAll(flattenList((List<Object>) e));

            }
        });

        return newList;

    }


    public static void main(String[] args) {

        List<Object> listOfList = new ArrayList<>();

        listOfList.add(Arrays.asList(1));
        listOfList.add(Arrays.asList(2, 3));
        listOfList.add(Arrays.asList(4, Arrays.asList(5, 6)));
        listOfList.add(Arrays.asList(7, Arrays.asList(Arrays.asList(8, Arrays.asList(9, 10)), 11)));

        List<Object> flattenedList = flattenList(listOfList);

        System.out.println("flattenedList = " + flattenedList);

    }
}
