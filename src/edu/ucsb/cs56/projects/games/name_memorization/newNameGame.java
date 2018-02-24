package edu.ucsb.cs56.projects.games.name_memorization;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
  /**
   * Preliminary engine for running a name memorization game
   *
   *@author Anthony Hoang, Colin Biafore
   *@author Domenic DiPeppe
   *@version  for CS56, W16
   */

public class NameGame extends Application{

    //Main JPanel
    //private StackPane nameGame;

    //Top Control Panel
    private JPanel north;
    private Button add;
    private JButton edit;
    private JButton delete;
    private JButton next;
    private JButton previous;
    private JButton selectDeck;
    private JButton menu;

    //Bottom Control Panel
    private JPanel south;
    private JPanel southQuiz;
    private JButton toFront;
    private JButton toBack;
    private JButton guess;
    private JTextField guessText;

    //East Control Panel
    private JLabel deckName;
    private JButton restart;

    //West Control Panel
    JPanel west = new JPanel();
    JPanel westQuiz = new JPanel();
    private JLabel scoreLabel;
    private JLabel scoreNum;
    private JLabel scoreLabelQuiz;
    private JLabel scoreNumQuiz;
    private int score;
    private int scoreQuiz;

    private JLabel deckSize;
    private JLabel sizeLabel;

    private JButton correct;
    private JButton incorrect;
    private JButton correctQuiz;

    private Label picture;
    private Image pic;

    //DeckList for Decks
    private DeckList decks;

    //Current Card Viewer
    private JPanel currentCard;
    private JTextArea cardText;
    private int current;
    private Deck d;

    //Deck viewer
    private JPanel DeckEditor;

    //Label for Card Number:
    private JLabel cardNum;
    //UI Card Index
    private JLabel cNum;

    private static JDialog myStart;

    /**
     * No arg constructor for the name game. Initializes everyting in a JFrame
     * (Buttons, pics, etc)
     */
    public NameGame(DeckList decks) {

    }
    
    @Override 
    public void start(Stage primaryStage) {
	//Set Frame Layout
	StackPane nameGame = new StackPane();
	//this.add(nameGame);
	nameGame.setLayout(new BorderLayout());
	score=0;
	//Initialize North Control Panel
	north = new Scene(thisFrame);
	//north.setVisible(true);
	add = new Button("Add");
	
	edit = new JButton("Edit");
	delete = new JButton("Delete");
	previous = new JButton("Previous");
	next = new JButton("Next");
	selectDeck = new JButton("Select Deck");
	menu = new JButton("Main Menu");
	north.add(add);
	north.add(Box.createRigidArea(new Dimension(20, 20)));
	north.add(edit);
	north.add(Box.createRigidArea(new Dimension(20, 20)));
	north.add(delete);
	north.add(Box.createRigidArea(new Dimension(20, 20)));
	north.add(previous);
	north.add(Box.createRigidArea(new Dimension(20, 20)));
	north.add(next);
	north.add(Box.createRigidArea(new Dimension(20, 20)));
	north.add(selectDeck);
	north.add(Box.createRigidArea(new Dimension(20,20)));
	north.add(menu);
	north.setBackground(Color.lightGray);
	nameGame.add(north,BorderLayout.NORTH);
	
	//Initialize South Control Panel
	south = new JPanel();
	south.setVisible(true);
	toFront = new JButton("Show Front");
	toBack = new JButton("Show Back");
	south.add(toFront);
	south.add(Box.createRigidArea(new Dimension(50, 50)));
	south.add(toBack);
	south.setBackground(Color.lightGray);
	nameGame.add(south, BorderLayout.SOUTH);
	
	// South Quiz Mode
	southQuiz = new JPanel();
	southQuiz.setVisible(true);
	guess = new JButton("Guess!");
	guessText = new JTextField("Enter Guess Here", 30);
	southQuiz.add(guessText);
	southQuiz.add(Box.createRigidArea(new Dimension(10, 50)));
	southQuiz.add(guess);
	southQuiz.setBackground(Color.lightGray);
	
	
	//Initialize Card Viewer
	currentCard = new JPanel();
	currentCard.setVisible(true);
	cardText = new JTextArea();
	Font cardFont = new Font("Verdana",Font.BOLD,24);
	cardText.setFont(cardFont);
	cardText.setEditable(false);
	currentCard.add(cardText);
	currentCard.setBackground(Color.WHITE);
	nameGame.add(currentCard, BorderLayout.CENTER);
	
	//decks is set in Main
	this.decks = decks;
	this.d = decks.get(0);
	if(d.size() == 0) cardText.setText("Deck is Empty!");
	
	//West Panel Components
	JPanel westCenter = new JPanel();
	westCenter.setBackground(Color.BLUE);
	west.setLayout(new BorderLayout());
	west.setBackground(Color.BLUE);
	
	scoreLabel= new JLabel("Score:");
	scoreLabel.setForeground(Color.WHITE);
	scoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	
	scoreNum = new JLabel(Integer.toString(score));
	scoreNum.setForeground(Color.WHITE);
	scoreNum.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
	JPanel westSouth = new JPanel();
	westSouth.setBackground(Color.BLUE);
	westSouth.setLayout(new BoxLayout(westSouth,BoxLayout.Y_AXIS));
	correct = new JButton("      Correct!      ");
	incorrect = new JButton("     Incorrect     ");
	westSouth.add(correct);
	westSouth.add(Box.createRigidArea(new Dimension(0, 50)));
	westSouth.add(incorrect);
	westSouth.add(Box.createRigidArea(new Dimension(0,200)));
	
	westCenter.add(scoreLabel);
	westCenter.add(scoreNum);
	
	west.add(westCenter,BorderLayout.CENTER);
	west.add(westSouth,BorderLayout.SOUTH);
	
	deckName = new JLabel(d.getName());
	deckName.setForeground(Color.WHITE);
	deckName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	west.add(deckName,BorderLayout.NORTH);
		
	nameGame.add(west,BorderLayout.WEST);
		
	//West panel Quiz
	JPanel westCenterQuiz = new JPanel();
	westCenterQuiz.setBackground(Color.BLUE);
	westQuiz.setLayout(new BorderLayout());
	westQuiz.setBackground(Color.BLUE);
	
	scoreLabelQuiz= new JLabel("Score:");
	scoreLabelQuiz.setForeground(Color.WHITE);
	scoreLabelQuiz.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	
	scoreNumQuiz = new JLabel(Integer.toString(scoreQuiz));
	scoreNumQuiz.setForeground(Color.WHITE);
	scoreNumQuiz.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
	JPanel westSouthQuiz = new JPanel();
	westSouthQuiz.setBackground(Color.BLUE);
	westSouthQuiz.setLayout(new BoxLayout(westSouthQuiz,BoxLayout.Y_AXIS));
	correctQuiz = new JButton("Override Correct");
	correctQuiz.setVisible(false);
		
	JLabel space = new JLabel();
	space= new JLabel("AAAAAAAAAAAA "); //set width of left panel
	space.setForeground(Color.BLUE);
	space.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	westSouthQuiz.add(space);
	westSouthQuiz.add(correctQuiz);
		
	westCenterQuiz.add(scoreLabelQuiz);
	westCenterQuiz.add(scoreNumQuiz);
	
	westQuiz.add(westCenterQuiz,BorderLayout.CENTER);
	westQuiz.add(westSouthQuiz,BorderLayout.SOUTH);
		
	deckName = new JLabel(d.getName());
	deckName.setForeground(Color.WHITE);
	deckName.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	westQuiz.add(deckName,BorderLayout.NORTH);
		
	//East Panel
	JPanel east = new JPanel();
	east.setLayout(new BorderLayout());
	east.setBackground(Color.BLUE);
	
	sizeLabel = new JLabel("Deck Size :");
	sizeLabel.setForeground(Color.WHITE);
	sizeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	deckSize = new JLabel( Integer.toString(d.size()));
	deckSize.setForeground(Color.WHITE);
	deckSize.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
	cardNum = new JLabel("Card Number:");
	cardNum.setForeground(Color.WHITE);
	cardNum.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
	cNum = new JLabel(Integer.toString(current));
	cNum.setForeground(Color.WHITE);
	cNum.setFont(new Font("Lucida Grande",Font.PLAIN, 18));
		
	restart = new JButton(" Restart ");
	east.add(restart, BorderLayout.SOUTH);
	
	JPanel eastCenter = new JPanel();
	eastCenter.setBackground(Color.BLUE);
	eastCenter.add(cardNum);
	eastCenter.add(cNum);
	
	JPanel top = new JPanel();
	top.setBackground(Color.BLUE);
	top.add(sizeLabel, BorderLayout.NORTH);
	top.add(deckSize, BorderLayout.NORTH);
	
	east.add(eastCenter,BorderLayout.CENTER);
	east.add(top,BorderLayout.NORTH);
	nameGame.add(east,BorderLayout.EAST);
	
	//BUTTON LISTENERS -- Uncommented = implemented and functioning!!
	//Currently you can add as many cards as you want,
	//Go to the next or previous card in the deck,
	//And see both sides of the current card
	
	//Initialize Add Button Listener
	addButtonListener addListener = new addButtonListener();
	add.addActionListener(addListener);
	
	//Initialize Edit Button Listener
	editButtonListener editListener = new editButtonListener();
	edit.addActionListener(editListener);
		
	//Initialize Delete Button Listener
	deleteButtonListener deleteListener = new deleteButtonListener();
	delete.addActionListener(deleteListener);
	
	//Initialize Previous Button Listener
	previousButtonListener previousListener = new previousButtonListener();
	previous.addActionListener(previousListener);
        
	//Initialize Next Button Listener
	nextButtonListener nextListener = new nextButtonListener();
	next.addActionListener(nextListener);
	
	//Initialize SelectDeck Button Listener
	selectDeckButtonListener selectDeckListener = new selectDeckButtonListener();
	selectDeck.addActionListener(selectDeckListener);
	
	menuButtonListener menuListener = new menuButtonListener();
	menu.addActionListener(menuListener);
		
	//Initialize Override correct Button Listener
	overcorrectButtonListener overcorrectListener = new overcorrectButtonListener();
	correctQuiz.addActionListener(overcorrectListener);
	
	//Initialize Guess Button Listener
	guessButtonListener guessListener = new guessButtonListener();
	guess.addActionListener(guessListener);
	
	//Initialize Front Button Listener
	frontButtonListener frontListener = new frontButtonListener();
	toFront.addActionListener(frontListener);
	
	//Initialize Back Button Listener
	backButtonListener backListener = new backButtonListener();
	toBack.addActionListener(backListener);
	
	correctButtonListener correctListener = new correctButtonListener();
	correct.addActionListener(correctListener);
	
	incorrectButtonListener incorrectListener = new incorrectButtonListener();
	incorrect.addActionListener(incorrectListener);
	
	restartButtonListener restartListener = new restartButtonListener();
	restart.addActionListener(restartListener);
	
	this.pack();
	
	myStart = new JDialog(thisFrame , "Welcome", true);
	myStart.setLayout( new FlowLayout() );
	JButton b = new JButton ("OK");
	b.addActionListener ( new ActionListener()
	    {
		public void actionPerformed( ActionEvent e )
		{
		    NameGame.myStart.setVisible(false);
		}
	    });
	myStart.add( new JLabel ("Welcome to the Name Memorization Game."));
	myStart.add( new JLabel ("For instructions and mode changing click Main Menu in the game."));
	myStart.add(b);
	myStart.add(b);
	myStart.setSize(500,130);
	myStart.setVisible(true);
}
