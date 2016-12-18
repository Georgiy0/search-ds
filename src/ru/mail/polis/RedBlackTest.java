package ru.mail.polis;

import java.util.List;
import java.util.Scanner;

/**
 * Created by kubri on 12/17/2016.
 */
public class RedBlackTest {
    public static void main(String[] args) {
        RedBlackTree<Integer> Tree = new RedBlackTree<>();
        Scanner sc = new Scanner(System.in);
        int menu;
        int input;
        while(true){
            System.out.println("1 - add; 2 - search; 3 - delete; 4 - print:");
            menu = sc.nextInt();
            switch (menu){
                case 1:
                    System.out.println("input int: ");
                    input = sc.nextInt();
                    Tree.add(input);
                    break;
                case 2:
                    System.out.println("search int: ");
                    sc.nextLine();
                    input = sc.nextInt();
                    System.out.println(Tree.contains(input));
                    break;
                case 3:
                    System.out.println("delete int: ");
                    sc.nextLine();
                    input = sc.nextInt();
                    System.out.println(Tree.remove(input));
                    break;
                case 4:
                    List<Integer> list = Tree.inorderTraverse();
                    if(list == null)
                        break;
                    for(Integer i : list){
                        System.out.print(i+" ");
                    }
                    System.out.println();

                    break;
                default:
                    return;
            }
        }
    }
}
