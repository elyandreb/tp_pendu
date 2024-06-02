import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton qui permet d'obtenir des informations'
     */ 
    private Button boutonInfo;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;
    /**
     * le bouton radio qui met le jeu en mode Facile
     */
    private RadioButton boutonFacile;
    /**
     * le bouton radio qui met le jeu en mode Moyen
     */ 
    private RadioButton boutonMedium;
    /**
     * le bouton radio qui met le jeu en mode Difficile
     */ 
    private RadioButton boutonDifficile;
    /**
     * le bouton radio qui met le jeu en mode Expert
     */ 
    private RadioButton boutonExpert;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.chrono = new Chronometre();
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral); //this.panelCentral
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){       
        BorderPane fenetre = new BorderPane(); 
        fenetre.setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));
        HBox banniere = new HBox();
        banniere.setPadding(new Insets(10));   
        Label titre = new Label("Jeu du Pendu");
        titre.setPadding(new Insets(30));
        titre.setFont(Font.font("Verdana", 40));

        this.boutonMaison = new Button();
        boutonMaison.setAlignment(Pos.CENTER_RIGHT);
        ImageView imgMaison = new ImageView(new Image("file:img/home.png"));
        imgMaison.setFitHeight(30); 
        imgMaison.setFitWidth(30); 
        boutonMaison.setGraphic(imgMaison);
    
        this.boutonParametres = new Button();
        boutonParametres.setAlignment(Pos.CENTER_RIGHT);
        ImageView imgParam = new ImageView(new Image("file:img/parametres.png"));
        imgParam.setFitHeight(30);
        imgParam.setFitWidth(30);
        boutonParametres.setGraphic(imgParam); 
        
        this.boutonInfo = new Button();
        boutonInfo.setAlignment(Pos.CENTER_RIGHT);
        ImageView imgInfo = new ImageView(new Image("file:img/info.png"));
        imgInfo.setFitHeight(30);
        imgInfo.setFitWidth(30);
        boutonInfo.setGraphic(imgInfo);
        
        banniere.getChildren().addAll(titre,boutonMaison,boutonParametres,boutonInfo);    

        fenetre.setLeft(titre);  
        fenetre.setRight(banniere); 
        return fenetre;
            

    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    private Pane fenetreJeu(){
        BorderPane res = new BorderPane();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50));
        this.motCrypte = new Text();
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        this.motCrypte.setFont(Font.font("Verdana", 40));
        this.dessin = new ImageView(new Image("file:img/pendu0.png"));
        this.pg = new ProgressBar();
        this.pg.setProgress(0.0);
        this.pg.setPrefWidth(200);
        this.pg.setPadding(new Insets(40));

        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this),8);

        
        vbox.getChildren().addAll(motCrypte,this.dessin,this.pg,clavier);

        VBox vbox2 = new VBox();
        vbox2.setPadding(new Insets(30));
        Label niveau = new Label();
        niveau.setText("Niveau : "+this.niveaux.get(this.modelePendu.getNiveau()));
        niveau.setFont(Font.font("Verdana", 20));
        
        TitledPane tp = new TitledPane();
        tp.setText("Chronomètre");
        tp.setContent(this.chrono);
        tp.setPadding(new Insets(10));  

        Button boutonMot = new Button(); 
        boutonMot.setText("Nouveau mot");
        boutonMot.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        
        vbox2.getChildren().addAll(niveau,tp, boutonMot);

        res.setLeft(vbox);
        res.setRight(vbox2);


        return res;
    }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    private Pane fenetreAccueil(){   
        VBox res = new VBox();
        res.setPadding(new Insets(10));
        this.bJouer = new Button(); 
        this.bJouer.setText("Lancer une partie");  
        this.bJouer.setPadding(new Insets(10)); 
        this.bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));

        VBox vb = new VBox();

        TitledPane tp = new TitledPane();
        tp.setText("Niveau de difficulté");
        tp.setContent(vb);
        
        ToggleGroup groupe = new ToggleGroup();
        
        this.boutonFacile = new RadioButton(String.valueOf(MotMystere.FACILE));
        this.boutonFacile.setText("Facile");    
        this.boutonFacile.setToggleGroup(groupe); 
        this.boutonFacile.setOnAction(new ControleurNiveau(modelePendu,MotMystere.FACILE));

        this.boutonMedium = new RadioButton(String.valueOf(MotMystere.MOYEN));
        this.boutonMedium.setText("Moyen");
        this.boutonMedium.setToggleGroup(groupe);
        this.boutonMedium.setOnAction(new ControleurNiveau(modelePendu,MotMystere.MOYEN));   

        this.boutonDifficile = new RadioButton(String.valueOf(MotMystere.DIFFICILE));
        this.boutonDifficile.setText("Difficile");
        this.boutonDifficile.setToggleGroup(groupe);  
        this.boutonDifficile.setOnAction(new ControleurNiveau(modelePendu,MotMystere.DIFFICILE));    

        this.boutonExpert = new RadioButton(String.valueOf(MotMystere.EXPERT));
        this.boutonExpert.setText("Expert");
        this.boutonExpert.setToggleGroup(groupe); 
        this.boutonExpert.setOnAction(new ControleurNiveau(modelePendu,MotMystere.EXPERT)); 

        vb.getChildren().addAll(this.boutonFacile,this.boutonMedium,this.boutonDifficile,this.boutonExpert);
        res.getChildren().addAll(this.bJouer,tp);    
        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral.setCenter(this.fenetreAccueil());
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(this.fenetreJeu());
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
        this.modelePendu.setMotATrouver();
        this.chrono.resetTime();
        this.chrono.start();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        
        // Mettre à jour le mot crypté
    this.motCrypte.setText(this.modelePendu.getMotCrypte());

    // Mettre à jour l'image du pendu
    int nbErreurs = this.modelePendu.getNbErreursRestants();
    if (nbErreurs < this.lesImages.size()) {
        this.dessin.setImage(this.lesImages.get(modelePendu.getNbErreursMax()-nbErreurs));
    }

    // Mettre à jour la barre de progression
    double progress = (double) nbErreurs / this.modelePendu.getNbErreursMax();
    this.pg.setProgress(progress);

    // Désactiver les touches déjà devinées
    this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());

    // Vérifier les conditions de victoire ou de défaite
    if (this.modelePendu.gagne()) {
        this.popUpMessageGagne().show();
        this.modeAccueil();
    } else if (this.modelePendu.perdu()) {
        this.popUpMessagePerdu().show();
        this.modeAccueil();
    }
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez gagné");        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez perdu"+"le mot à trouver était "+this.modelePendu.getMotATrouve());
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        this.panelCentral = new BorderPane();
        this.niveaux = Arrays.asList("Facile", "Médium", "Difficile", "Expert");
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
