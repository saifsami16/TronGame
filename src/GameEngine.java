import com.company.Move;
import com.company.Tron;
import javax.swing.*;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameEngine extends JPanel implements ActionListener {

    public final static int width = 850;
    public final static int height = 480;

    PreparedStatement insertStatement;
    //PreparedStatement deleteStatement;
    Connection connection;



    private Image background = new ImageIcon("data/bg.jpg").getImage();
    Tron tron1;
    Tron tron2;
    //String Winner;
    //Boolean running = true;
    private Timer timer;
    Move move;

    public GameEngine(String plr1, String plr2, String clr1, String clr2) {
        connectDB();
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        initializeGame(plr1, plr2, clr1,clr2);
        addKeyListener(new Keys());
    }
    public GameEngine(){
        connectDB();
    }
    public void connectDB(){
        try {
            String dbURL = "jdbc:sqlite:/home/samijr/Documents/5th Semester/Programming Tech/Third Assignment/database.db";
            connection = DriverManager.getConnection(dbURL);
            //Statement statement = connection.createStatement();
            //statement.execute("CREATE TABLE HIGHSCORES (NAME VARCHAR, SCORE INTEGER)");


            String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        draw(g);
    }
    public void initializeGame(String plr1, String plr2, String clr1, String clr2) {
        tron1 = new Tron();
        tron2 = new Tron();
        move = new Move();
        tron1.setSize(10000);  //The size of the trail left behind by the trons
        tron2.setSize(10000);
        tron1.setTronName(plr1);
        tron2.setTronName(plr2);
        tron1.setTronColor(clr1);
        tron2.setTronColor(clr2);
        for (int i = 0; i < tron1.getSize(); i++) {
            tron1.setTronX(0, 100);
            tron1.setTronY(0, 200);
        }
        for (int i = 0; i < tron2.getSize(); i++) {
            tron2.setTronX(0, 800);
            tron2.setTronY(0, 200);
        }
        tron1.setMovingRight(true);
        tron2.setMovingLeft(true);

        timer = new Timer(40, this);    //Speed of the tron
        timer.start();
    }

    void draw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        //Tron1
        for (int i = 0; i < tron1.getSize(); i++) {
            g.setColor(tron1.getTronColor());
            g.fillRect(tron1.getTronX(i), tron1.getTronY(i), 5, 5); //The size of the rectangle
        }
        //Tron2
        for (int i = 0; i < tron2.getSize(); i++) {
            g.setColor(tron2.getTronColor());
            g.fillRect(tron2.getTronX(i), tron2.getTronY(i), 5, 5);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /*public void restart(){
        System.out.println("play again");
    }*/

    public void endGame(String Winner)
    {
        timer.stop();
        putRecordsIntoDatabase(Winner);
        JOptionPane.showMessageDialog(this,
                "\t" + Winner + " wins\n\nIf you want to play again, select restart from Menu",
                "Winner",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        move.player(tron1);
        move.player(tron2);
        if(collides(tron1, tron2))endGame(tron2.getTronName());     //check if tron1 collides with tron 2's path
        else if(collides(tron2, tron1))endGame(tron1.getTronName());     //check if tron2 collides with tron 1's path
        else repaint();
    }

    //check if the trons collide with each other or the boundary or themselves
    public boolean collides(Tron t1, Tron t2) {
        for(int i = 1; i < tron1.getSize(); i++){
            // if it collides with itself
            if(t1.getTronX(0) == t1.getTronX(i) &&  t1.getTronY(0)==t1.getTronY(i) && t1.getTronX(i) != 0)return true;
            // if it collides with the other one
            if(t1.getTronX(0) == t2.getTronX(i) &&  t1.getTronY(0)==t2.getTronY(i) && t1.getTronX(i) != 0)return true;
            // if it hits the boundary
            if(t1.getTronX(0) == 0 || t1.getTronX(0) == 850 || t1.getTronY(0) == 0 || t1.getTronY(0) == 480)return true;
        }
        return false;
    }

    private class Keys extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!tron2.isMovingRight())) {
                tron2.setMovingLeft(true);
                tron2.setMovingUp(false);
                tron2.setMovingDown(false);
            }
            if ((key == KeyEvent.VK_RIGHT) && (!tron2.isMovingLeft())) {
                tron2.setMovingRight(true);
                tron2.setMovingUp(false);
                tron2.setMovingDown(false);
            }
            if ((key == KeyEvent.VK_UP) && (!tron2.isMovingDown())) {
                tron2.setMovingUp(true);
                tron2.setMovingRight(false);
                tron2.setMovingLeft(false);
            }
            if ((key == KeyEvent.VK_DOWN) && (!tron2.isMovingUp())) {
                tron2.setMovingDown(true);
                tron2.setMovingRight(false);
                tron2.setMovingLeft(false);
            }
            if ((key == KeyEvent.VK_A) && (!tron1.isMovingRight())) {
                tron1.setMovingLeft(true);
                tron1.setMovingUp(false);
                tron1.setMovingDown(false);
            }
            if ((key == KeyEvent.VK_D) && (!tron1.isMovingLeft())) {
                tron1.setMovingRight(true);
                tron1.setMovingUp(false);
                tron1.setMovingDown(false);
            }
            if ((key == KeyEvent.VK_W) && (!tron1.isMovingDown())) {
                tron1.setMovingUp(true);
                tron1.setMovingRight(false);
                tron1.setMovingLeft(false);
            }
            if ((key == KeyEvent.VK_S) && (!tron1.isMovingUp())) {
                tron1.setMovingDown(true);
                tron1.setMovingRight(false);
                tron1.setMovingLeft(false);
            }
        }
    }

    public void putRecordsIntoDatabase(String playerName){

        try{
            /*ArrayList<HighScore> sortedScores = getHighScores();
            int i = 0;
            boolean found = false;
            while(i < sortedScores.size() && !found){
                if(sortedScores.get(i).getName() == playerName){
                    insertStatement.setInt(2,sortedScores.get(i).getScore()+1);
                }
            }*/
            insertStatement.setString(1, playerName);
            insertStatement.setInt(2, 1);
            insertStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public ArrayList printDatabase() throws SQLException {
        ArrayList<HighScore> sortedScores = getHighScores();
        return sortedScores;
    }


    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }
}
