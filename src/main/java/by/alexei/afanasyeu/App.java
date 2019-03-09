package by.alexei.afanasyeu;

import by.alexei.afanasyeu.domain.RateLimiterConfig;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        TaskLauncher taskLauncher = new TaskLauncher();
        if (args.length > 0) {
            switch (args[0]) {
                case "1":
                    taskLauncher.startFirstTask();
                    break;
                case "2":
                    taskLauncher.startSecondTask();
                    break;
                case "3":
                    taskLauncher.startThirdTask();
                    break;
                case "4":
                    taskLauncher.startFourthTask();
                    break;
                case "5":
                    taskLauncher.startFifthTask();
                    break;
                case "6":
                    try {
                        RateLimiterConfig config = getRateLimiterConfig(args);
                        taskLauncher.startSixthTask(config);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        printHelp();
                    }
                    break;
                case "all":
                    try {
                        RateLimiterConfig config = getRateLimiterConfig(args);
                        taskLauncher.startAll(config);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        printHelp();
                    }
                    break;
                default: printHelp();
            }
        } else {
            System.out.println("Ошибка: отсутствуют необходимые параметры.\n");
            printHelp();
        }
    }

    private static RateLimiterConfig getRateLimiterConfig(String[] args) {
        if (args.length == 5) {
            int requests;
            int timeUnitCount;
            TimeUnit timeUnit;
            String rateLimiterType = args[4];
            int interval = 0;
            //validation requests
            try {
                requests = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Количество запросов должно быть числом.\n");
            }
            if (requests < 1) {
                throw new IllegalArgumentException("Количество запросов должно быть больше 0.\n");
            }
            //validation timeUnitCount
            try {
                timeUnitCount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Количество временных единиц должно быть числом.\n");
            }
            if (timeUnitCount < 1) {
                throw new IllegalArgumentException("Количество временных единиц должно быть больше 0.\n");
            }
            //validation timeUnit
            switch(args[3]) {
                case TaskLauncher.SEC :
                    timeUnit = TimeUnit.SECONDS;
                    break;
                case TaskLauncher.MIN :
                    timeUnit = TimeUnit.MINUTES;
                    break;
                case TaskLauncher.HOUR:
                    timeUnit = TimeUnit.HOURS;
                    break;
                case TaskLauncher.DAY :
                    timeUnit = TimeUnit.DAYS;
                    break;
                default:
                    throw new IllegalArgumentException("Временная единица '"+args[3]+"' не доступна.\n");
            }
            switch(timeUnit) {
                case SECONDS:
                    interval = 1000*timeUnitCount;
                    break;
                case MINUTES:
                    interval = 1000*60*timeUnitCount;
                    break;
                case HOURS:
                    interval = 1000*60*60*timeUnitCount;
                    break;
                case DAYS:
                    interval = 1000*60*60*24*timeUnitCount;
                    break;
            }
            //validation rateLimiterType
            if (!rateLimiterType.equals(TaskLauncher.GENERAL) && !rateLimiterType.equals(TaskLauncher.STRICT)) {
                throw new IllegalArgumentException("Тип RateLimiter'a '" + args[4] + "' не доступен.\n");
            }
            return new RateLimiterConfig(requests, interval, rateLimiterType);
        } else {
            throw new IllegalArgumentException("Для запуска задания с ограничителем количества запросов задания необходимо 5 параметров.\n");
        }
    }

    private static void printHelp() {
        System.out.println(
                        "Примеры запуска: апрою.jar 1\n" +
                        "   java -jar wgforge.jar 1  ->  запуск 1-го задания.\n" +
                        "   java -jar wgforge.jar 2  ->  запуск 2-го задания.\n" +
                        "   java -jar wgforge.jar 3  ->  запуск 3-го задания.\n" +
                        "   java -jar wgforge.jar 4  ->  запуск 4-го задания.\n" +
                        "   java -jar wgforge.jar 5  ->  запуск 5-го задания.\n" +
                        "   java -jar wgforge.jar 6 600 1 min general  ->  запуск 6-го задания. Параметры:\n" +
                        "       600 -> лимит запросов;\n" +
                        "       1   -> количество временных единиц, за которое можно отправить лимит запросов;\n" +
                        "       min -> тип временных единиц, доступны:\n" +
                        "               sec - секунды;\n" +
                        "               min - минуты;\n" +
                        "               hour - часы;\n" +
                        "               day - дни;\n" +
                        "       general -> тип RateLimiter'а доступны:\n" +
                        "               general - быстрый, но работающий с погрешностью ограничитель;\n" +
                        "               strict  - гарантирует ограничение на любом отрезке времени;\n" +
                        "   java -jar wgforge.jar all 600 1 min general  ->  запуск всех заданий. Параметры как в 6-ом задании.\n");
    }
}