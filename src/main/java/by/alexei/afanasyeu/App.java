package by.alexei.afanasyeu;

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
                    taskLauncher.startSixthTask();
                    break;
                case "all":
                    break;
                default: printHelp();
            }
        } else {
            System.out.println("нет параметров");
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("Для запуска введите в качестве параметра номер задания.\n" +
                "Пример: апрою.jar 1\n" +
                "Для прекращения Ctrl + c");
    }
}