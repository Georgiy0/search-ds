package ru.mail.polis;

import java.util.Scanner;

/**
 * Created by kubri on 12/17/2016.
 */
public class OpenHashTest {
    public static void main(String[] args) {
        OpenHashTable<String> Hash = new OpenHashTable<>();
        Scanner sc = new Scanner(System.in);
        int menu;
        String input;
        while(true){
            System.out.println("1 - add; 2 - search; 3 - delete; 4 - print:");
            menu = sc.nextInt();
            switch (menu){
                case 1:
                    System.out.println("input string: ");
                    sc.nextLine();
                    input = sc.nextLine();
                    Hash.add(input);
                    break;
                case 2:
                    System.out.println("search string: ");
                    sc.nextLine();
                    input = sc.nextLine();
                    System.out.println(Hash.contains(input));
                    break;
                case 3:
                    System.out.println("delete string: ");
                    sc.nextLine();
                    input = sc.nextLine();
                    System.out.println(Hash.remove(input));
                    break;
                case 4:
                    Hash.print();
                    break;
                default:
                    return;
            }
        }
    }
}
