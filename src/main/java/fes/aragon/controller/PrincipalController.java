package fes.aragon.controller;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.util.Duration;
public class PrincipalController implements Initializable{
    public Button btnQuicksort;
    Data<String, Number> primero = null;
    Data<String, Number> segundo = null;
    Data<String, Number> menor =null;
    int tiempoRetardo=40;
    int numeroDatos=40;
    @FXML
    private BarChart<String, Number> bacGrafica;
    @FXML
    private Button btnBurbuja;
    @FXML
    private Button btnSacudida;
    @FXML
    private Button btnSeleccion;
    @FXML
    private Button btnInsercion;
    @FXML
    private Button btnListaNueva;
    @FXML
    void metodoBurbuja(ActionEvent event) {
        this.btnListaNueva.setDisable(true);
        this.btnBurbuja.setDisable(true);
        this.btnQuicksort.setDisable(true);
        this.btnSeleccion.setDisable(true);
        this.btnInsercion.setDisable(true);
        this.btnSacudida.setDisable(true);

        Task<Void> animateSortTask = burbujaTask(bacGrafica.getData().get(0));
        exec.submit(animateSortTask);

    }
    @FXML
    void metodoQuicksort(ActionEvent event) {
        this.btnListaNueva.setDisable(true);
        this.btnBurbuja.setDisable(true);
        this.btnQuicksort.setDisable(true);
        this.btnSeleccion.setDisable(true);
        this.btnInsercion.setDisable(true);
        this.btnSacudida.setDisable(true);
        Task<Void> animateSortTask = quicksortTask(bacGrafica.getData().get(0));
        exec.submit(animateSortTask);
    }
    @FXML
    void metodoSacudida(ActionEvent event) {
        this.btnListaNueva.setDisable(true);
        this.btnBurbuja.setDisable(true);
        this.btnQuicksort.setDisable(true);
        this.btnSeleccion.setDisable(true);
        this.btnInsercion.setDisable(true);
        this.btnSacudida.setDisable(true);
        Task<Void> animateSortTask = metodoSacudida(bacGrafica.getData().get(0));
        exec.submit(animateSortTask);

    }
    @FXML
    void metodoListaNueva(ActionEvent event) {
        bacGrafica.getData().clear();

        Series<String, Number> series = new Series<String, Number>();
        series = generarAleatoriosEnteros(numeroDatos);
        bacGrafica.getData().add(series);

    }
    @FXML
    void metodoInsercion(ActionEvent event) {
        this.btnListaNueva.setDisable(true);
        this.btnBurbuja.setDisable(true);
        this.btnQuicksort.setDisable(true);
        this.btnSeleccion.setDisable(true);
        this.btnInsercion.setDisable(true);
        this.btnSacudida.setDisable(true);
        Task<Void> animateSortTask = insercionTask(bacGrafica.getData().get(0));
        exec.submit(animateSortTask);

    }
    @FXML
    void metodoSeleccion(ActionEvent event) {
        this.btnListaNueva.setDisable(true);
this.btnBurbuja.setDisable(true);
this.btnQuicksort.setDisable(true);
        this.btnInsercion.setDisable(true);
        this.btnSeleccion.setDisable(true);
        this.btnSacudida.setDisable(true);
        Task<Void> animateSortTask = seleccionTask(bacGrafica.getData().get(0));
        exec.submit(animateSortTask);
    }
    private Task<Void> seleccionTask(Series<String, Number> series) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Data<String, Number>> data = series.getData();
                for (int i = 0; i < data.size() - 1; i++) {
                    int k = i;
                    menor = data.get(i);

                    // Resaltar el nodo que estamos considerando como mínimo
                    Platform.runLater(() -> {
                        menor.getNode().setStyle("-fx-background-color: green;");
                    });
                    Thread.sleep(tiempoRetardo);

                    for (int j = i + 1; j < data.size(); j++) {
                        Data<String, Number> actual = data.get(j);

                        // Cambiar el color para mostrar que estamos comparando este valor
                        Platform.runLater(() -> {
                            actual.getNode().setStyle("-fx-background-color: blue;");
                        });
                        Thread.sleep(tiempoRetardo);

                        if (actual.getYValue().doubleValue() < menor.getYValue().doubleValue()) {
                            // Resetear el color del anterior mínimo
                            Platform.runLater(() -> {
                                menor.getNode().setStyle("-fx-background-color: green;");
                            });

                            k = j;
                            menor = actual;

                            // Resaltar el nuevo mínimo
                            Platform.runLater(() -> {
                                menor.getNode().setStyle("-fx-background-color: green;");
                            });
                        }
                        Thread.sleep(tiempoRetardo);

                        // Resetear el color del nodo comparado si no es el mínimo
                        Platform.runLater(() -> {
                            actual.getNode().setStyle("-fx-background-color: red;");
                        });
                    }

                    // Intercambiar el valor mínimo encontrado con el valor en la posición i
                    if (k != i) {
                        Data<String, Number> actual = data.get(i);
                        CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            Animation swap = createSwapAnimation(actual, menor);
                            swap.setOnFinished(e -> latch.countDown());
                            swap.play();
                        });
                        latch.await();
                    }

                    // Resetear el color del nodo seleccionado
                    Platform.runLater(() -> {
                        menor.getNode().setStyle("-fx-background-color: none;");
                    });
                    Thread.sleep(tiempoRetardo);
                }
                btnListaNueva.setDisable(false);
                btnBurbuja.setDisable(false);
                btnQuicksort.setDisable(false);
                btnInsercion.setDisable(false);
                btnSeleccion.setDisable(false);
                return null;
            }
        };
    }




    private Task<Void> burbujaTask(Series<String, Number> series) {

        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Data<String, Number>> data = series.getData();
                for (int i = data.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < i; j++) {
                        primero = data.get(j);
                        segundo = data.get(j + 1);
                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: red ;");

                            segundo.getNode().setStyle("-fx-background-color: blue ;");

                        });
                        Thread.sleep(tiempoRetardo);
                        if (primero.getYValue().doubleValue() >=

                                segundo.getYValue().doubleValue()) {

                            CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                Animation swap =

                                        createSwapAnimation(primero, segundo);

                                swap.setOnFinished(e -> latch.countDown());
                                swap.play();
                            });
                            latch.await();
                        }
                        Thread.sleep(tiempoRetardo);
                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: blue ;");

                            segundo.getNode().setStyle("-fx-background-color: red ;");

                        });
                    }
                }
                btnListaNueva.setDisable(false);
                btnBurbuja.setDisable(false);
                btnQuicksort.setDisable(false);
                btnInsercion.setDisable(false);
                btnSeleccion.setDisable(false);
                return null;
            }
        };
    }

    private Task<Void> insercionTask(Series<String, Number> series) {

        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Data<String, Number>> data = series.getData();
for(int h=0;h<data.size();h++) {
    for (int i = 1; i < data.size(); i++) {
        Data<String, Number> actual = data.get(i);
        int j = i - 1;

        Platform.runLater(() -> actual.getNode().setStyle("-fx-background-color: red;"));
        Thread.sleep(tiempoRetardo);

        while (j >= 0 && data.get(j).getYValue().doubleValue() > actual.getYValue().doubleValue()) {
            Data<String, Number> anterior = data.get(j);

            Platform.runLater(() -> anterior.getNode().setStyle("-fx-background-color: green;"));
            Thread.sleep(tiempoRetardo);

            CountDownLatch latch = new CountDownLatch(1);
            int finalJ = j;
            Platform.runLater(() -> {

                Animation swap = createSwapAnimation(anterior, data.get(finalJ + 1));
                swap.setOnFinished(e -> latch.countDown());
                swap.play();
            });
            latch.await();

            Platform.runLater(() -> anterior.getNode().setStyle("-fx-background-color: blue;"));
            j--;
            Thread.sleep(tiempoRetardo);
        }

        Platform.runLater(() -> actual.getNode().setStyle("-fx-background-color: blue;"));
        Thread.sleep(tiempoRetardo);
    }
}

                btnListaNueva.setDisable(false);
                btnBurbuja.setDisable(false);
                btnQuicksort.setDisable(false);
                btnInsercion.setDisable(false);
                btnSeleccion.setDisable(false);

                return null;
            }
        };
    }







    //METODO QUICKORT
    private Task<Void> quicksortTask(Series<String, Number> series) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Data<String, Number>> data = series.getData();
                quicksort(data, 0, data.size() - 1);
                Platform.runLater(() -> btnListaNueva.setDisable(false)); // Habilitar botón al terminar
                return null;
            }
        };
    }
    private void quicksort(ObservableList<Data<String, Number>> data, int menor, int mayor) throws InterruptedException {
        if (menor < mayor) {
            int pivoteIn = particion(data, menor, mayor);  // Partición alrededor del pivote
            quicksort(data, menor, pivoteIn - 1);  // Ordenar sublista izquierda
            quicksort(data, pivoteIn + 1, mayor);  // Ordenar sublista derecha
        }
    }

    private int particion(ObservableList<Data<String, Number>> data, int menor, int may) throws InterruptedException {
        Data<String, Number> pivot = data.get(may);  // Último elemento como pivote
        Platform.runLater(() -> pivot.getNode().setStyle("-fx-background-color: green;"));  // Resaltar el pivote
        int i = menor - 1;

        for (int j = menor; j < may; j++) {
            Data<String, Number> current = data.get(j);
            if (current.getYValue().doubleValue() < pivot.getYValue().doubleValue()) {
                i++;
                Data<String, Number> smallerElement = data.get(i);
                Platform.runLater(() -> {
                    current.getNode().setStyle("-fx-background-color: red;");
                    smallerElement.getNode().setStyle("-fx-background-color: blue;");
                });
                Thread.sleep(tiempoRetardo);

                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    Animation swap = createSwapAnimation(smallerElement, current);
                    swap.setOnFinished(e -> latch.countDown());
                    swap.play();
                });
                latch.await();
            }
        }

        Data<String, Number> elementAtIPlusOne = data.get(i + 1);
        Platform.runLater(() -> {
            pivot.getNode().setStyle("-fx-background-color: blue;");
            elementAtIPlusOne.getNode().setStyle("-fx-background-color: green;");
        });
        Thread.sleep(tiempoRetardo);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Animation swap = createSwapAnimation(elementAtIPlusOne, pivot);
            swap.setOnFinished(e -> latch.countDown());
            swap.play();
        });
        latch.await();
        btnListaNueva.setDisable(false);
        btnBurbuja.setDisable(false);
        btnQuicksort.setDisable(false);
        btnInsercion.setDisable(false);
        btnSeleccion.setDisable(false);
        return i + 1;
    }
    //**********************************************************************//

    private Task<Void> metodoSacudida(Series<String, Number> series) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Data<String, Number>> data = series.getData();
                boolean swapped = true;
                int start = 0;
                int end = data.size() - 1;

                while (swapped) {
                    swapped = false;

                    // Primera pasada (de izquierda a derecha)
                    for (int i = start; i < end; i++) {
                        primero = data.get(i);
                        segundo = data.get(i + 1);

                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: red;");
                            segundo.getNode().setStyle("-fx-background-color: blue;");
                        });
                        Thread.sleep(tiempoRetardo);

                        if (primero.getYValue().doubleValue() > segundo.getYValue().doubleValue()) {
                            CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                Animation swap = createSwapAnimation(primero, segundo);
                                swap.setOnFinished(e -> latch.countDown());
                                swap.play();
                            });
                            latch.await();
                            swapped = true;
                        }

                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: blue;");
                            segundo.getNode().setStyle("-fx-background-color: red;");
                        });
                        Thread.sleep(tiempoRetardo);
                    }

                    // Si no se hicieron intercambios, no es necesario seguir
                    if (!swapped) break;

                    // Reducir el rango final porque el último elemento ya está ordenado
                    end--;

                    swapped = false;

                    // Segunda pasada (de derecha a izquierda)
                    for (int i = end; i > start; i--) {
                        primero = data.get(i - 1);
                        segundo = data.get(i);

                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: red;");
                            segundo.getNode().setStyle("-fx-background-color: blue;");
                        });
                        Thread.sleep(tiempoRetardo);

                        if (primero.getYValue().doubleValue() > segundo.getYValue().doubleValue()) {
                            CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                Animation swap = createSwapAnimation(primero, segundo);
                                swap.setOnFinished(e -> latch.countDown());
                                swap.play();
                            });
                            latch.await();
                            swapped = true;
                        }

                        Platform.runLater(() -> {
                            primero.getNode().setStyle("-fx-background-color: blue;");
                            segundo.getNode().setStyle("-fx-background-color: red;");
                        });
                        Thread.sleep(tiempoRetardo);
                    }

                    // Incrementar el rango inicial porque el primer elemento ya está ordenado
                    start++;

                    // Si no hubo intercambios en esta pasada, ya está ordenado
                    if (!swapped) break;
                }

                btnListaNueva.setDisable(false);
                btnBurbuja.setDisable(false);
                btnQuicksort.setDisable(false);
                btnInsercion.setDisable(false);
                btnSeleccion.setDisable(false);
                btnSacudida.setDisable(false);

                return null;
            }
        };
    }


    //**********************************************************************//

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
// TODO Auto-generated method stub
        bacGrafica.setAnimated(false);
        Series<String, Number> series = new Series<String, Number>();

        series = generarAleatoriosEnteros(numeroDatos);

        bacGrafica.getData().add(series);

    }
    private Series<String, Number> generarAleatoriosEnteros(int n) {
        Series<String, Number> series = new Series<>();
        Random rnd = new Random();
        for (int i = 1; i <= n; i++) {
            series.getData().add(new Data<>(String.valueOf(i), rnd.nextInt(90) + 10));
        }
        return series;
    }
    private <T> Animation createSwapAnimation(Data<?, T> primero, Data<?, T> segundo) {
        double primeroX =

                primero.getNode().getParent().localToScene(primero.getNode().getBoundsInParent()).getMinX();

        double segundoX =

                primero.getNode().getParent().localToScene(segundo.getNode().getBoundsInParent()).getMinX();

        double primeroStartTranslate = primero.getNode().getTranslateX();
        double segundoStartTranslate = segundo.getNode().getTranslateX();
        TranslateTransition primeroTranslate = new TranslateTransition(Duration.millis(tiempoRetardo), primero.getNode());

        primeroTranslate.setByX(segundoX - primeroX);
        TranslateTransition sgundoTranslate = new TranslateTransition(Duration.millis(tiempoRetardo),

                segundo.getNode());

        sgundoTranslate.setByX(primeroX - segundoX);
        ParallelTransition translate = new ParallelTransition(primeroTranslate, sgundoTranslate);
        translate.statusProperty().addListener((obs, oldStatus, newStatus) -> {
            if (oldStatus == Animation.Status.RUNNING) {
                T temp = primero.getYValue();
                primero.setYValue(segundo.getYValue());
                segundo.setYValue(temp);
                primero.getNode().setTranslateX(primeroStartTranslate);
                segundo.getNode().setTranslateX(segundoStartTranslate);
            }
        });
        return translate;
    }
    private ExecutorService exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });
}
