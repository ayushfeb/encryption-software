
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
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
import java.util.zip.ZipOutputStream;
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
public class EncryptionTest extends javax.swing.JFrame {

    static SecretKey secKey;
    static Cipher aesCipher;
    static String fileName;
    static String keyFileName;  
    static KeyGenerator keyGen;
    static byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    static IvParameterSpec ivspec = new IvParameterSpec(iv);

    public EncryptionTest() {
        initComponents();
    }
    public static void deleteFilesAfterZip(String path){
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
    public static void addToZipFile(ZipOutputStream zos, String fileName)throws Exception{
		
	File file=new File(fileName);
	FileInputStream fis = new FileInputStream(file);
	ZipEntry zipEntry=new ZipEntry(new File(fileName).getName());
	zos.putNextEntry(zipEntry);
	
	byte[] bytes=new byte[1024];
	int length;
	while((length = fis.read(bytes))>=0) {
		zos.write(bytes, 0, length);	
	}
	zos.closeEntry();
	fis.close();	
			
	}
    private static byte[] convertPDFToByteArray() {

	        InputStream inputStream = null;
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {

	            inputStream = new FileInputStream(fileName);

	            byte[] buffer = new byte[1024];
	            baos = new ByteArrayOutputStream();

	            int bytesRead;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                baos.write(buffer, 0, bytesRead);
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return baos.toByteArray();
	        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pathTextFld = new javax.swing.JTextField();
        fileSelectionBtn = new javax.swing.JButton();
        encryptionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 300));

        fileSelectionBtn.setText(". . .");
        fileSelectionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSelectionBtnActionPerformed(evt);
            }
        });

        encryptionBtn.setText("Encrypt");
        encryptionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(pathTextFld, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fileSelectionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(encryptionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pathTextFld, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileSelectionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addComponent(encryptionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encryptionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptionBtnActionPerformed
        try {
            // TODO add your handling code here:
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
	        keyGen.init(128); 
        try {
            aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        secKey = keyGen.generateKey();
	   
		            FileOutputStream fos;
        try {
            keyFileName=fileName+"_key.txt";
            fos = new FileOutputStream(keyFileName);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(secKey);
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }            
	        byte[] byteText = convertPDFToByteArray();
        try {
            // System.out.println(secKey);
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey,ivspec);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
	        byte[] byteCipherText;
        try {
            byteCipherText = aesCipher.doFinal(byteText);
            Files.write(Paths.get(fileName), byteCipherText);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileOutputStream fosZip = new FileOutputStream(fileName+".zip");
            ZipOutputStream zos = new ZipOutputStream(fosZip);
            
            
            addToZipFile(zos,fileName);
            addToZipFile(zos,keyFileName);
            
            zos.close();
            fosZip.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EncryptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        deleteFilesAfterZip(fileName);
        deleteFilesAfterZip(keyFileName);
        JOptionPane.showMessageDialog(rootPane, "your File has been Encrypted");
    }//GEN-LAST:event_encryptionBtnActionPerformed

    private void fileSelectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSelectionBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser=new JFileChooser();
        int returnValue=fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
            pathTextFld.setText(fileName);
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
            java.util.logging.Logger.getLogger(EncryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EncryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EncryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EncryptionTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EncryptionTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton encryptionBtn;
    private javax.swing.JButton fileSelectionBtn;
    private javax.swing.JTextField pathTextFld;
    // End of variables declaration//GEN-END:variables
}
