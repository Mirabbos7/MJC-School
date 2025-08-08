package com.mjc.school;

import com.mjc.school.controller.Controller;
import com.mjc.school.repository.impl.DataSourceImpl;
import com.mjc.school.repository.dataSource.AuthorData;
import com.mjc.school.repository.dataSource.NewsData;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.impl.NewsServiceImpl;
import com.mjc.school.service.validators.Validator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int operation = 0;
        Scanner scanner = new Scanner(System.in);

        AuthorData authorDataSource = new AuthorData();
        NewsData newsDataSource = new NewsData(authorDataSource);
        DataSourceImpl dataSource = new DataSourceImpl(authorDataSource, newsDataSource);

        NewsService newsService = new NewsServiceImpl(dataSource, Validator.getInstance());
        Controller controller = new Controller(newsService);

        do {
            System.out.println("Choose operation:\n1. Get all news\n2. Get news by id\n3. Create news\n4. Update news\n5. Delete news\n0. Exit");
            operation = scanner.nextInt();
            switch (operation) {
                case 1 -> controller.getAllNews().forEach(System.out::println);
                case 2 -> {
                    System.out.println("Enter new id:");
                    System.out.println(controller.getNewsById(scanner.nextLong()));
                }
                case 3 -> {
                    System.out.println("Enter news title:");
                    String title = scanner.next();
                    System.out.println("Enter news content:");
                    String content = scanner.next();
                    System.out.println("Enter news author id:");
                    Long authorId = scanner.nextLong();
                    System.out.println(controller.createNews(new NewsDTO(title, content, authorId)));
                }
                case 4 -> {
                    System.out.println("Enter news id:");
                    Long id = scanner.nextLong();
                    System.out.println("Enter news title:");
                    String title = scanner.next();
                    System.out.println("Enter news content:");
                    String content = scanner.next();
                    System.out.println("Enter news author id:");
                    Long authorId = scanner.nextLong();
                    System.out.println(controller.updateNews(id,new NewsDTO(title, content, authorId)));
                }
                case 5 ->{
                    System.out.println("Enter news id:");
                    controller.deleteNews(scanner.nextLong());
                }

                case 0 ->{
                    System.out.println("Goodbye!");
                    scanner.close();
                }
            }
        } while (operation != 0);
    }
}
