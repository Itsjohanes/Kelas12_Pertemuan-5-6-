/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johanes.tokohapevisual.controller;

import com.johanes.tokohapevisual.DAO.ProductDAOImp;
import com.johanes.tokohapevisual.model.Product;
import javax.swing.JOptionPane;
import com.johanes.tokohapevisual.view.ProductForm;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Johannes Alexander Putra, CSCU
 */
public class ProductController {
    
    
    public void simpan(ProductForm form){
        try {
            Product product = new Product();
            String kategori = form.txtKategori.getText();
            String produk = form.txtProduk.getText();
            double harga = Double.parseDouble(form.txtHarga.getText());
            int kuantitas = Integer.parseInt(form.txtKuantitas.getText());

            product.setProductCategory(kategori);
            product.setProductName(produk);
            product.setPrice(harga);
            product.setQty(kuantitas);

            ProductDAOImp dao = new ProductDAOImp();
            dao.insert(product);
            loadDB(form);
            clearTextField(form);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     public void loadDB(ProductForm form)
    {
        try {
            ProductDAOImp productDAO = new ProductDAOImp();
            List<Product> listProduct = productDAO.list();
            DefaultTableModel DFT = (DefaultTableModel) form.tableProduct.getModel();
            DFT.setRowCount(0);

            for(Product product: listProduct)
            {
                int productId = product.getProductId();
                String productCategory = product.getProductCategory();
                String productName = product.getProductName();
                double price = product.getPrice();
                int qty = product.getQty();
                DFT.addRow(new Object[]{productId,productCategory,productName,price,qty});
            } 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     public void delete(ProductForm form){
          // TODO add your handling code here:
        try {

            int productId = Integer.parseInt(form.txtIDProduct.getText());
            String produk = form.txtProduk.getText();

            int pesan = JOptionPane.showOptionDialog(form, "Anda yakin akan menghapus produk " +produk+ "?", "Konfirmasi Hapus Produk", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(pesan == JOptionPane.YES_OPTION){
                Product product = new Product();
                product.setProductId(productId);
                ProductDAOImp dao = new ProductDAOImp();
                dao.delete(product);
                loadDB(form);
                clearTextField(form);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
     public void clearTextField(ProductForm form){
        
        form.txtIDProduct.setText("");
        form.txtKategori.setText("");
        form.txtProduk.setText("");
        form.txtHarga.setText("");
        form.txtKuantitas.setText("");
         
     }
     public void edit(ProductForm form){
          // TODO add your handling code here:
        try {
            Product product = new Product();
            int productId = Integer.parseInt(form.txtIDProduct.getText());
            String kategori = form.txtKategori.getText();
            String produk = form.txtProduk.getText();
            double harga = Double.parseDouble(form.txtHarga.getText());
            int kuantitas = Integer.parseInt(form.txtKuantitas.getText());
            product.setProductCategory(kategori);
            product.setProductName(produk);
            product.setPrice(harga);
            product.setQty(kuantitas);
            product.setProductId(productId);
        
            ProductDAOImp dao = new ProductDAOImp();
            dao.update(product);
            loadDB(form);
            clearTextField(form);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
     public void cari(ProductForm form){
          // TODO add your handling code here:
        try {
            int productId = Integer.parseInt(JOptionPane.showInputDialog("Masukan ID Produk"));
            ProductDAOImp dao = new ProductDAOImp();
            Product productDAO = dao.getProduct(productId);
            loadDBByID(form,productId);
            form.txtIDProduct.setText(String.valueOf(productDAO.getProductId()));            
            form.txtKategori.setText(productDAO.getProductCategory());
            form.txtProduk.setText(productDAO.getProductName());
            form.txtHarga.setText(String.format("%.0f", productDAO.getPrice()));
            form.txtKuantitas.setText(String.valueOf(productDAO.getQty()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
     public void click(ProductForm form){
         try {

            int row =form.tableProduct.getSelectedRow();
            String dataKlik=(form.tableProduct.getModel().getValueAt(row, 0).toString());
            int productId = Integer.parseInt(dataKlik);

            ProductDAOImp dao = new ProductDAOImp();
            Product productDAO = dao.getProduct(productId);

            form.txtIDProduct.setText(String.valueOf(productDAO.getProductId()));            
            form.txtKategori.setText(productDAO.getProductCategory());
            form.txtProduk.setText(productDAO.getProductName());
            form.txtHarga.setText(String.format("%.0f", productDAO.getPrice()));
            form.txtKuantitas.setText(String.valueOf(productDAO.getQty()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
     public void loadDBByID(ProductForm form,int productId){
         
          try {
            ProductDAOImp productDAO = new ProductDAOImp();
            List<Product> listProduct = productDAO.listByID(productId);
            DefaultTableModel DFT = (DefaultTableModel) form.tableProduct.getModel();
            DFT.setRowCount(0);

            for(Product product: listProduct)
            {
                int IDproduct = product.getProductId();
                String productCategory = product.getProductCategory();
                String productName = product.getProductName();
                double price = product.getPrice();
                int qty = product.getQty();
                DFT.addRow(new Object[]{IDproduct,productCategory,productName,price,qty});
            } 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
}
