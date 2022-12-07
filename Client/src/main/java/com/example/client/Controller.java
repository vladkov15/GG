package com.example.client;

import com.example.client.Models.Product;
import com.example.client.Models.ProductPost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Controller {
    ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private Button Refresh;

    @FXML
    private Button Add;

    @FXML
    private TextField amount;

    @FXML
    private TextField decription;

    @FXML
    private Button Delete;

    @FXML
    private TableColumn<Product, String> Description;

    @FXML
    private TableColumn<Product, String> Name;

    @FXML
    private TableColumn<Product, String> Amount;

    @FXML
    private TableColumn<Product, String> FirstDate;

    @FXML
    private TableColumn<Product, String> SecondDate;

    @FXML
    private TextField firstDate;

    @FXML
    private TextField name;

    @FXML
    private Button Search;

    @FXML
    private TextField secondDate;

    @FXML
    private Button Send;

    @FXML
    private TableView<Product> Table = new TableView<Product>(products);

    @FXML
    private Button Update;

    @FXML
    void btnAddClick(ActionEvent event) throws Exception {
        ProductPost product = new ProductPost(name.getText(),Integer.valueOf(amount.getText()),
                firstDate.getText(),secondDate.getText(),decription.getText());
        SendPost(product);
    }

    @FXML
    void btnClickDelete(ActionEvent event) throws Exception {
       int id = Table.getSelectionModel().getSelectedItem().id;
       SendDelete(id);
    }

    @FXML
    void btnSearchClick(ActionEvent event) {

    }

    @FXML
    void btnSendClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateClick(ActionEvent event) {

    }



    @FXML
    void btnRefreshClick(ActionEvent event) throws Exception {
        SendGet();
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        FirstDate.setCellValueFactory(new PropertyValueFactory<>("date_of_create"));
        SecondDate.setCellValueFactory(new PropertyValueFactory<>("expiration_date"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Table.setItems(products);

    }

    public void SendDelete(int id) throws Exception{
        URL url = new URL("http://localhost:3307/products/"+Integer.toString(id));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Content-Type", "application/json");
        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        System.out.println(response);
    }

    public void SendPost(ProductPost product) throws Exception {
        URL url = new URL("http://localhost:3307/products");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(new Gson().toJson(product).getBytes());
        os.flush();
        os.close();
        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        System.out.println(response);
    }

    public void SendGet() throws Exception{
        URL url = new URL("http://localhost:3307/products");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        System.out.println(response);

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        Product[] myResponse = gson.fromJson(String.valueOf(response), Product[].class);
        products.addAll(myResponse);
        }
}
