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
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.fenetreAccueil()); //this.panelCentral
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
        Pane res = new Pane();
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
        //TitledPane tp = new TitledPane("Niveau de difficulté");
        

        ToggleGroup groupe = new ToggleGroup();

        this.boutonFacile = new RadioButton(String.valueOf(MotMystere.FACILE));
        this.boutonFacile.setText("Facile");    
        this.boutonFacile.setToggleGroup(groupe); 

        this.boutonMedium = new RadioButton(String.valueOf(MotMystere.MOYEN));
        this.boutonMedium.setText("Moyen");
        this.boutonMedium.setToggleGroup(groupe);

        this.boutonDifficile = new RadioButton(String.valueOf(MotMystere.DIFFICILE));
        this.boutonDifficile.setText("Difficile");
        this.boutonDifficile.setToggleGroup(groupe);  

        this.boutonExpert = new RadioButton(String.valueOf(MotMystere.EXPERT));
        this.boutonExpert.setText("Expert");
        this.boutonExpert.setToggleGroup(groupe);   

        res.getChildren().addAll(this.bJouer,this.boutonFacile,this.boutonMedium,this.boutonDifficile,this.boutonExpert);    
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
        // A implementer
    }
    
    public void modeJeu(){
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
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
