package explorer;

import explorer.dialog.CreateNewFolderJDialog;
import explorer.dialog.RenameJDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInterface extends JFrame {

    private JPanel catalogPanel = new JPanel();
    private JList filesList = new JList<>();
    private JScrollPane filesScroll = new JScrollPane(filesList);
    private JPanel buttonsPanel = new JPanel();

    private JButton removeButton = new JButton("Удалить");
    private JButton addButton = new JButton("Добавить папку");
    private JButton renameButton = new JButton("Переименовать");
    private JButton backButton = new JButton("Назад");

    private List dirsCache = new ArrayList<>();

    public UserInterface() {
        super("Проводник");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        catalogPanel.setLayout(new BorderLayout(5,5));
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.setLayout(new GridLayout(1, 4, 5, 5));

        JDialog createNewDirDialog = new JDialog(UserInterface.this, "Создание папки", true);
        JPanel createNewDirPanel = new JPanel();
        createNewDirDialog.add(createNewDirPanel);

        File[] discs = File.listRoots();

        filesScroll.setPreferredSize(new Dimension(400, 500));
        filesList.setListData(discs);
        filesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        filesList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultListModel model = new DefaultListModel();
                    String selectedObject = filesList.getSelectedValue().toString();
                    String fullPath = toFullPath(dirsCache);

                    File selectedFile;

                    if (dirsCache.size() > 1) {
                        selectedFile = new File(fullPath, selectedObject);
                    } else {
                        selectedFile = new File(fullPath + selectedObject);
                    }

                    if (selectedFile.isDirectory()) {
                        String[] rootStr = selectedFile.list();
                        for (String s : rootStr) {
                            File checkObject = new File(selectedFile.getPath(), s);
                            if (!checkObject.isHidden()) {
                                if (checkObject.isDirectory()) {
                                    model.addElement(s);
                                } else {
                                    model.addElement("файл - " + s);
                                }
                            }
                        }
                        dirsCache.add(selectedObject);
                        filesList.setModel(model);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dirsCache.size()  > 1) {
                    DefaultListModel backRootModel = new DefaultListModel();
                    dirsCache.remove(dirsCache.size() - 1);

                    String backDir = toFullPath(dirsCache);
                    String[] objects = new File(backDir).list();

                    for (String str : objects) {
                        File checkFile = new File(backDir, str);
                        if (!checkFile.isHidden()) {
                            if (checkFile.isDirectory()) {
                                backRootModel.addElement(str);
                            } else {
                                backRootModel.addElement("файл -" + str);
                            }
                        }
                    }
                    filesList.setModel(backRootModel);
                } else {
                    dirsCache.removeAll(dirsCache);
                    filesList.setListData(discs);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dirsCache.isEmpty()) {
                    String currentPath;
                    File newFolder;

                    CreateNewFolderJDialog newFolderJDialog = new CreateNewFolderJDialog(UserInterface.this);

                    if (newFolderJDialog.isReady()) {
                        currentPath = toFullPath(dirsCache);
                        newFolder = new File(currentPath, newFolderJDialog.getNewFolderName());

                        if (!newFolder.exists()) {
                            newFolder.mkdir();

                            File updateDir = new File(currentPath);
                            String[] updateMas = updateDir.list();
                            DefaultListModel updateModel = new DefaultListModel();

                            for (String s : updateMas) {
                                File check = new File(updateDir.getPath(), s);
                                if (!check.isHidden()) {
                                    if (check.isDirectory()) {
                                        updateModel.addElement(s);
                                    } else {
                                        updateModel.addElement("файл - " + s);
                                    }
                                }
                            }
                            filesList.setModel(updateModel);
                        }
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirsCache);

                if (!selectedObject.isEmpty()) {
                    deleteDir(new File(currentPath, selectedObject));

                    File updateDir = new File(currentPath);
                    String[] updateMas = updateDir.list();
                    DefaultListModel updateModel = new DefaultListModel();

                    for (String str : updateMas) {
                        File check = new File(updateDir.getPath(), str);
                        if (!check.isHidden()) {
                            if (check.isDirectory()) {
                                updateModel.addElement(str);
                            } else {
                                updateModel.addElement("файл - " + str);
                            }
                        }
                    }
                    filesList.setModel(updateModel);
                }
            }
        });

        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dirsCache.isEmpty() & filesList.getSelectedValue() != null) {
                    String currentPath = toFullPath(dirsCache);
                    String selectedObject = filesList.getSelectedValue().toString();

                    RenameJDialog rename = new RenameJDialog(UserInterface.this);
                    if (rename.isReady()) {
                        File renameFile = new File(currentPath);
                        renameFile.renameTo(new File(currentPath, rename.getName()));

                        File updateDir = new File(currentPath);
                        String[] updateMas = updateDir.list();
                        DefaultListModel updateModel = new DefaultListModel();

                        for (String str : updateMas) {
                            File check = new File(updateDir.getPath(), str);
                            if (!check.isHidden()) {
                                if (check.isDirectory()) {
                                    updateModel.addElement(str);
                                } else {
                                    updateModel.addElement("файл - " + str);
                                }
                            }
                        }
                        filesList.setModel(updateModel);
                    }
                }
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(renameButton);

        catalogPanel.setLayout(new BorderLayout());
        catalogPanel.add(filesScroll, BorderLayout.CENTER);
        catalogPanel.add(buttonsPanel, BorderLayout.SOUTH);

        getContentPane().add(catalogPanel);
        setSize(600,  600);
//        setLocation(null);
        setVisible(true);
    }

    private String toFullPath(List<String> dirsCache) {
        String current = "";
        for (String str : dirsCache) {
            current += str;
        }
        return current;
    }

    private void deleteDir(File file) {
        File[] current = file.listFiles();
        if (current != null) {
            for (File f : current) {
                deleteDir(f);
            }
        }
        file.delete();
    }

}
