package explorer.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameJDialog extends JDialog {
    private JTextField nameOfNewFolder = new JTextField();
    private JButton renameButton = new JButton("Переименовать");
    private JButton cancelButton = new JButton("Отмена");

    private String newFolderName;
    private boolean ready = false;

    private JLabel newFolderWait = new JLabel("Новое имя папки: ");

    public RenameJDialog(JFrame jFrame) {
        super(jFrame, "Переименовать папку", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(400, 200);

        renameButton.addActionListener(new ActionListener() {
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
        getContentPane().add(renameButton);
        getContentPane().add(cancelButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getNameOfNewFolder() {
        return nameOfNewFolder;
    }

    public JButton getRenameButton() {
        return renameButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public boolean isReady() {
        return ready;
    }

    public JLabel getNewFolderWait() {
        return newFolderWait;
    }
}
