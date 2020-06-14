import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lenovo
 */
public class DecryptionTest extends javax.swing.JFrame {

   static String fileName;
   static String keyFileName;
   static SecretKey secKey;
   static String parentFileName;
   static KeyGenerator keyGen;
   static String pdfFileName;
    static Cipher aesCipher;
    static byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    static IvParameterSpec ivspec = new IvParameterSpec(iv);
   
   
   
    public DecryptionTest() {
        initComponents();
    }

    public static void deleteFilesAfterUnzip(String path){
        try
        { 
            Files.deleteIfExists(Paths.get(path)); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful.");
    
    }
    
    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pathTxtFld = new javax.swing.JTextField();
        fileSelectionBtn = new javax.swing.JButton();
        decryptionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pathTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathTxtFldActionPerformed(evt);
            }
        });

        fileSelectionBtn.setText(". . .");
        fileSelectionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSelectionBtnActionPerformed(evt);
            }
        });

        decryptionBtn.setText("Decrypt");
        decryptionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(pathTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(fileSelectionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(decryptionBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileSelectionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105)
                .addComponent(decryptionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void decryptionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptionBtnActionPerformed
       try {
           keyGen = KeyGenerator.getInstance("AES");
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
       }
	keyGen.init(128);
        File file=new File(fileName);
        parentFileName=file.getParent();
        unzip(fileName, parentFileName);
        FileInputStream fis=null;
        try {
           // TODO add your handling code here:
           keyFileName=new File(fileName).getName();
           keyFileName=keyFileName.substring(0, keyFileName.length()-4)+"_key.txt";
           pdfFileName = new File(fileName).getName();
           pdfFileName= pdfFileName.substring(0, pdfFileName.length()-4);
           
           fis = new FileInputStream(parentFileName+"\\"+keyFileName);
           ObjectInputStream ois= new ObjectInputStream(fis);
           secKey=(SecretKey)ois.readObject();
           
           aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
           
           byte[] cipherText = Files.readAllBytes(Paths.get(parentFileName+"\\"+pdfFileName));
           aesCipher.init(Cipher.DECRYPT_MODE,secKey, ivspec);
           byte[] bytePlainText = aesCipher.doFinal(cipherText);
           try {
               //byte[] base64decodedTokenArr = Base64.decodeBase64(cipherText);
	       //byte[] bytePlainText = aesCipher.doFinal(base64decodedTokenArr);
                Files.write(Paths.get(parentFileName+"\\"+pdfFileName), bytePlainText);
           } catch (IOException ex) {
               Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
           }
       } catch (FileNotFoundException ex) {
           Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException | ClassNotFoundException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
           Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
       } catch (NoSuchPaddingException ex) {
           Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
       } finally {
           try {
               fis.close();
           } catch (IOException ex) {
               Logger.getLogger(DecryptionTest.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        deleteFilesAfterUnzip(fileName);
        deleteFilesAfterUnzip(parentFileName+"\\"+keyFileName);
        JOptionPane.showMessageDialog(rootPane, "your File has been Decrypted");
        
    }//GEN-LAST:event_decryptionBtnActionPerformed

    private void pathTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathTxtFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathTxtFldActionPerformed

    private void fileSelectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSelectionBtnActionPerformed
        // TODO add your handling code here:
         JFileChooser fileChooser=new JFileChooser();
        int returnValue=fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
            pathTxtFld.setText(fileName);
        }
    }//GEN-LAST:event_fileSelectionBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DecryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DecryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DecryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DecryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DecryptionTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decryptionBtn;
    private javax.swing.JButton fileSelectionBtn;
    private javax.swing.JTextField pathTxtFld;
    // End of variables declaration//GEN-END:variables
}
