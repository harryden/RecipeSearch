package recipesearch;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;

public class RecipeListItem extends AnchorPane {
    private RecipeSearchController parentController;
    private Recipe recipe;

    @FXML private ImageView recipeImageView;
    @FXML private Label recipeLabel;
    @FXML private ImageView recipeCuisine;
    @FXML private ImageView recipeMainIngredient;
    @FXML private ImageView recipeDifficulty;
    @FXML private Label recipeMaxTime;
    @FXML private Label recipePrice;
    @FXML private Label recipeDescription;



    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;
        this.recipeImageView.setImage(recipe.getFXImage());
        this.recipeLabel.setText(recipe.getName());
        this.recipeCuisine.setImage(parentController.getCuisineImage(recipe.getCuisine()));
        this.recipeMainIngredient.setImage(parentController.getMainIngredientImage(recipe.getMainIngredient()));
        this.recipeDifficulty.setImage(parentController.getDifficultyImage(recipe.getDifficulty()));

    }

    @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(recipe);
    }

    public Image getSquareImage(Image image){

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if(image.getWidth() > image.getHeight()){
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int)(image.getWidth() - width)/2;
            y = 0;
        }

        else if(image.getHeight() > image.getWidth()){
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height)/2;
        }

        else{
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }
}
