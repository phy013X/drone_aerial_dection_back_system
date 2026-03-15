package com.expressway;

import com.expressway.utils.PasswordUtils;

import java.util.Scanner;

public class PasswordGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要加密的密码明文：");
        String password = scanner.nextLine();
        
        String encryptedPassword = PasswordUtils.encrypt(password);
        System.out.println("加密后的密码暗文：" + encryptedPassword);
        
        scanner.close();
    }
}
