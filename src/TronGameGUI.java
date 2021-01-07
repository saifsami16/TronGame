
import com.company.Tron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;


public class TronGameGUI {
    private JFrame frame;
    JPanel mainMenu;
    JPanel game = new JPanel();
    JTextField tron1, tron2;

    private GameEngine gameArea;
    JPanel high = new JPanel();
    String color[] = {"RED", "GREEN", "BLUE"};
    public TronGameGUI() {
        frame = new JFrame("Tron");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem highScoreMenuItem = new JMenuItem("Highscores");
        gameMenu.add(highScoreMenuItem);
        highScoreMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.setVisible(false);
                mainMenu.setVisible(false);
                high.setVisible(false);
                high = new JPanel();
                GameEngine g = new GameEngine();
                try {

                    ArrayList<HighScore> hh= g.printDatabase();
                    JTextArea txtArea = new JTextArea();

                    for (int i= 0; i < hh.size() && i<10; i++){
                        txtArea.setText( txtArea.getText()+" \n "+hh.get(i).toString());
                    }
                    txtArea.setEditable(false);
                    high.add(txtArea);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                high.setVisible(true);
                frame.add(high);
            }
        });

        JMenuItem restartMenuItem = new JMenuItem("Restart");
        gameMenu.add(restartMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        mainMenu = new JPanel();
        mainMenu.setLayout(new BorderLayout());
        tron1 = new JTextField("");
        tron2 = new JTextField("");
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.PAGE_AXIS));
        // create a label to display text
        JLabel l1name = new JLabel("Enter name for Player 1");
        l1name.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel c1name = new JLabel("Choose color for Player 1");
        JLabel c2name = new JLabel("Choose color for Player 2");

        final JComboBox<String> l1color = new JComboBox<String>(color);

        l1color.setMaximumSize(l1color.getPreferredSize()); // added code
        l1color.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        //cb.setVisible(true); // Not needed
        JLabel l2name = new JLabel("Enter name for Player 2");
        l2name.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final JComboBox<String> l2color = new JComboBox<String>(color);

        l2color.setMaximumSize(l2color.getPreferredSize()); // added code
        l2color.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        mainMenu.add(l1name);
        mainMenu.add(tron1);
        mainMenu.add(c1name);
        mainMenu.add(l1color);
        mainMenu.add(l2name);
        mainMenu.add(tron2);
        mainMenu.add(c2name);
        mainMenu.add(l2color);
        JButton sub = new JButton("Submit");
        sub.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainMenu.add(sub);
        mainMenu.setVisible(true);

        restartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                high.setVisible(false);
                game.setVisible(false);
                mainMenu.setVisible(true);
            }
        });


        sub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false); //On pressing submit button, the panel becomes hidden
                high.setVisible(false);
                frame.update(frame.getGraphics());
                game = new JPanel();
                // Sending all the necessary details to the GameEngine Class
                String temp1, temp2;
                if(tron1.getText().isEmpty())temp1 = l1color.getSelectedItem().toString();
                else temp1 = tron1.getText();
                if(tron2.getText().isEmpty())temp2 = l2color.getSelectedItem().toString();
                else temp2 = tron2.getText();
                gameArea = new GameEngine(temp1,temp2, l1color.getSelectedItem().toString(), l2color.getSelectedItem().toString());
                game.add(gameArea);
                frame.add(game);
                game.setVisible(true);
                gameArea.setVisible(true);
            }
        });


        frame.add(mainMenu, BorderLayout.PAGE_START);
        frame.setPreferredSize(new Dimension(850, 480));    //The background image size
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }
}