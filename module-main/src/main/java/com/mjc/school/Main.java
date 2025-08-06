package com.mjc.school;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.impl.NewsService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int operationNumber = 1;
        NewsService newsService = new NewsService();
        NewsController controller = new NewsController(newsService);
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("""
                    Enter the number of operation:
                    1 - Get all news.
                    2 - Get news by id.
                    3 - Create news.
                    4 - Update news.
                    5 - Remove news by id.
                    0 - Exit.""");
            if (scanner.hasNextInt()) {
                operationNumber = scanner.nextInt();
            } else {
                scanner.nextLine();
                System.out.println("Command not found.");
                continue;
            }
            try {
                switch (operationNumber) {
                    case 1 -> {
                        System.out.println("All news");
                        controller.readAll().forEach(System.out::println);
                    }
                    case 2 -> {
                        scanner = new Scanner(System.in);
                        System.out.println("Enter id");
                        int id;
                        if (scanner.hasNextInt()) {
                            id = scanner.nextInt();
                            System.out.println(controller.readById(id));
                        } else {
                            throw new RuntimeException("INT_VALUE_OUT_OF_RANGE");
                        }
                    }
                    case 3 -> {
                        scanner = new Scanner(System.in);
                        System.out.println("creating news");
                        System.out.println("Enter title");
                        String title = scanner.nextLine();
                        System.out.println("Enter content");
                        String content = scanner.nextLine();
                        System.out.println("Enter Author id");
                        Long authorId = scanner.nextLong();
                        System.out.println(controller.create(new NewsRequestDto(title, content, authorId)));
                    }
                    case 4 -> {
                        scanner = new Scanner(System.in);
                        System.out.println("updating news");
                        System.out.println("Enter id");
                        if (scanner.hasNextLong()) {
                            scanner.nextLong();
                        } else {
                            throw new RuntimeException("INT_VALUE_OUT_OF_RANGE");
                        }
                        System.out.println("Enter title");
                        scanner.nextLine();
                        String title = scanner.nextLine();
                        System.out.println("Enter content.txt");
                        String content = scanner.nextLine();
                        System.out.println("Enter Author id");
                        long authId;
                        if (scanner.hasNextInt()) {
                            authId = scanner.nextLong();
                            System.out.println(controller.update(new NewsRequestDto(title, content, authId)));
                        } else {
                            throw new RuntimeException("INT_VALUE_OUT_OF_RANGE");
                        }
                    }
                    case 5 -> {
                        scanner = new Scanner(System.in);
                        System.out.println("Enter id");
                        long id;
                        if (scanner.hasNextInt()) {
                            id = scanner.nextLong();
                            System.out.println(controller.delete(id));
                        } else {
                            throw new RuntimeException("INT_VALUE_OUT_OF_RANGE");
                        }

                    }
                    case 0 -> System.out.println("exiting");

                    default -> System.out.println("Unknown command");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } while (operationNumber != 0);
    }
}