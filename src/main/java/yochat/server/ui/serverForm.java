/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yochat.server.ui;

import static yochat.server.handlers.ClientHandler.notifyEveryClient;
import static yochat.server.handlers.ServerStart.onlineUsers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import yochat.server.handlers.ServerStart;
import yochat.server.models.Paquet;
import yochat.server.models.User;
import yochat.server.utility.Command;

/**
 *
 * @author coulibai
 */
public class serverForm extends javax.swing.JFrame {

        /**
         * Creates new form ServerForm
         */
        public serverForm() {
                initComponents();
        }

        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                javax.swing.JDesktopPane jDesktopPane1 = new javax.swing.JDesktopPane();
                /**
                 * @param args the command line arguments
                 */
                // Variables declaration - do not modify//GEN-BEGIN:variables
                javax.swing.JLabel lblServer = new javax.swing.JLabel();
                btnStart = new javax.swing.JButton();
                btnRefresh = new javax.swing.JButton();
                btnClear = new javax.swing.JButton();
                btnList = new javax.swing.JButton();
                javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
                taConsole = new javax.swing.JTextArea();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jDesktopPane1.setBackground(new java.awt.Color(186, 161, 136));

                lblServer.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                lblServer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblServer.setText("SERVER");
                lblServer.setForeground(new java.awt.Color(255, 255, 255));

                btnStart.setBackground(new java.awt.Color(51, 102, 0));
                btnStart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnStart.setText("Start");
                btnStart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnStart.addActionListener(this::btnStartActionPerformed);

                btnRefresh.setBackground(new java.awt.Color(153, 0, 0));
                btnRefresh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnRefresh.setText("Refresh");
                btnRefresh.setToolTipText("Restart Server");
                btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                btnRefresh.setEnabled(false);
                btnRefresh.addActionListener(this::btnRefreshActionPerformed);

                btnClear.setBackground(new java.awt.Color(153, 153, 0));
                btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                btnClear.setText("Clear");
                btnClear.setEnabled(false);
                btnClear.addActionListener(this::jButton2ActionPerformed);

                btnList.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                btnList.setText("Users Online List");
                btnList.setEnabled(false);
                btnList.addActionListener(this::btnListActionPerformed);

                jDesktopPane1.setLayer(lblServer, javax.swing.JLayeredPane.DEFAULT_LAYER);
                jDesktopPane1.setLayer(btnStart, javax.swing.JLayeredPane.DEFAULT_LAYER);
                jDesktopPane1.setLayer(btnRefresh, javax.swing.JLayeredPane.DEFAULT_LAYER);
                jDesktopPane1.setLayer(btnClear, javax.swing.JLayeredPane.DEFAULT_LAYER);
                jDesktopPane1.setLayer(btnList, javax.swing.JLayeredPane.DEFAULT_LAYER);

                javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
                jDesktopPane1.setLayout(jDesktopPane1Layout);
                jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout
                                                .createSequentialGroup().addGap(21, 21, 21)
                                                .addComponent(btnList, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98,
                                                                Short.MAX_VALUE)
                                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(jDesktopPane1Layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(btnRefresh,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(btnStart,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                81,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21))
                                .addGroup(jDesktopPane1Layout.createSequentialGroup().addGap(175, 175, 175)
                                                .addComponent(lblServer, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)));
                jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout
                                                .createSequentialGroup()
                                                .addComponent(lblServer, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jDesktopPane1Layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(btnList,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                32, Short.MAX_VALUE)
                                                                .addComponent(btnClear,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(btnRefresh,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addGap(22, 22, 22)));

                taConsole.setEditable(false);
                taConsole.setBackground(new java.awt.Color(102, 102, 102));
                taConsole.setColumns(20);
                taConsole.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
                taConsole.setForeground(new java.awt.Color(255, 255, 255));
                taConsole.setRows(5);
                taConsole.setToolTipText("Affichage des instructions du server");
                taConsole.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                taConsole.setMargin(new java.awt.Insets(5, 5, 5, 5));
                jScrollPane1.setViewportView(taConsole);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jDesktopPane1).addGroup(layout.createSequentialGroup().addContainerGap()
                                                .addComponent(jScrollPane1).addContainerGap()));
                layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293,
                                                                Short.MAX_VALUE)
                                                .addContainerGap()));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        protected void btnRefreshActionPerformed(ActionEvent e) {
                btnStart.setEnabled(false);
                btnRefresh.setEnabled(false);
                btnList.setEnabled(false);
                btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                btnClear.setEnabled(false);
                btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                taConsole.setText("");

                try {
                        Thread.sleep(2000);
                        btnRefresh.setEnabled(true);
                        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        btnList.setEnabled(true);
                        btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        btnClear.setEnabled(true);
                        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Server not started");
                        Thread.currentThread().interrupt();
                }

                Paquet paquet = new Paquet(new User("SERVEUR"), " Tout le monde est déconnecté", Command.SERVER_ERROR);

                notifyEveryClient(paquet.toString(), null);

                // Supprimer tous les utilisateurs connectés
                ServerStart.deleteAllUsers();
                taConsole.append("Server Refresh successful\n");

        }

        protected void btnListActionPerformed(ActionEvent e) {
                taConsole.append("Liste des clients connectés :\n");
                for (User user : onlineUsers.keySet()) {
                        taConsole.append(" @" + user.getUsername() + "\n");
                        taConsole.setCaretPosition(taConsole.getDocument().getLength());
                }
        }

        protected void btnStartActionPerformed(ActionEvent e) {
                Thread startServer = new Thread(new ServerStart());
                startServer.start();
        }

        protected void jButton2ActionPerformed(ActionEvent e) {
                // effacer les contenues de taConsole
                taConsole.removeAll();
                taConsole.setText("");
        }

        public static javax.swing.JButton btnList;
        public static javax.swing.JButton btnStart;
        public static javax.swing.JButton btnRefresh;
        public static javax.swing.JButton btnClear;
        public static javax.swing.JTextArea taConsole;
        // End of variables declaration//GEN-END:variables
}
