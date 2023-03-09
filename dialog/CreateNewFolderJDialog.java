package explorer.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateNewFolderJDialog extends JDialog {

    private JTextField nameOfNewFolder = new JTextField();
    private JButton okButton = new JButton("Создать");
    private JButton cancelButton = new JButton("Отмена");

    private String newFolderName;
    private boolean ready = false;

    private JLabel newFolderWait = new JLabel("Имя новой папки: ");

    public CreateNewFolderJDialog(JFrame jFrame) {
        super(jFrame, "Создать новую папку", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(400, 200);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFolderName = nameOfNewFolder.getText();
                setVisible(false);
                ready = true;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFolderName = nameOfNewFolder.getText();
                setVisible(false);
                ready = false;
            }
        });

        getContentPane().add(newFolderWait);
        getContentPane().add(nameOfNewFolder);
        getContentPane().add(okButton);
        getContentPane().add(cancelButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean isReady() {
        return ready;
    }

    public JTextField getNameOfNewFolder() {
        return nameOfNewFolder;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public JLabel getNewFolderWait() {
        return newFolderWait;
    }
}
