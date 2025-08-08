package com.mjc.school;

import com.mjc.school.controller.Controller;
import com.mjc.school.repository.impl.DataSourceImpl;
import com.mjc.school.repository.dataSource.AuthorData;
import com.mjc.school.repository.dataSource.NewsData;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.impl.NewsServiceImpl;
import com.mjc.school.service.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        int operation = 0;
        Scanner scanner = new Scanner(System.in);

        AuthorData authorDataSource = new AuthorData();
        NewsData newsDataSource = new NewsData(authorDataSource);
        DataSourceImpl dataSource = new DataSourceImpl(authorDataSource, newsDataSource);

        NewsService newsService = new NewsServiceImpl(dataSource, Validator.getInstance());
        Controller controller = new Controller(newsService);

        do {
            logger.info("""
                    Choose operation:
                    1. Get all news
                    2. Get news by id
                    3. Create news
                    4. Update news
                    5. Delete news
                    0. Exit
                    """);

            while (!scanner.hasNextInt()) {
                logger.warn("Invalid input. Please enter a number.");
                scanner.next();
            }

            operation = scanner.nextInt();

            try {
                final String newsId = "Enter news id:";
                switch (operation) {
                    case 1 -> controller.getAllNews().forEach(news -> logger.info(news.toString()));
                    case 2 -> {
                        logger.info(newsId);
                        long id = scanner.nextLong();
                        logger.info("{}", controller.getNewsById(id));
                    }
                    case 3 -> {
                        scanner.nextLine();
                        logger.info("Enter news title:");
                        String title = scanner.nextLine();
                        logger.info("Enter news content:");
                        String content = scanner.nextLine();
                        logger.info("Enter news author id:");
                        Long authorId = scanner.nextLong();
                        logger.info("{}", controller.createNews(new NewsDTO(title, content, authorId)));
                    }
                    case 4 -> {
                        logger.info(newsId);
                        Long id = scanner.nextLong();
                        scanner.nextLine();
                        logger.info("Enter news title:");
                        String title = scanner.nextLine();
                        logger.info("Enter news content:");
                        String content = scanner.nextLine();
                        logger.info("Enter news author id:");
                        Long authorId = scanner.nextLong();
                        logger.info("{}", controller.updateNews(id, new NewsDTO(title, content, authorId)));
                    }
                    case 5 -> {
                        logger.info(newsId);
                        Long id = scanner.nextLong();
                        controller.deleteNews(id);
                        logger.info("News with id {} deleted.", id);
                    }
                    case 0 -> {
                        logger.info("Goodbye!");
                        scanner.close();
                    }
                    default -> logger.warn("Invalid operation: {}", operation);
                }
            } catch (Exception e) {
                logger.error("Error while processing operation {}: {}", operation, e.getMessage(), e);
                scanner.nextLine();
            }

        } while (operation != 0);
    }
}
