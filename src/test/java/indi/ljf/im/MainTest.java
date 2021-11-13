package indi.ljf.im;

import java.util.Scanner;

/**
 * @author: ljf
 * @date: 2021/10/22 10:26
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class MainTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String header = sc.next();
        if (header.equals("quit")) {
            System.exit(0);
        }
        String message = sc.next();
        System.out.println(header + " -> " + message);
    }
}
