
package recipesearch;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    @FXML FlowPane recipeFlowPane;

    private RecipeBackendController bc = new RecipeBackendController();
    private List<Recipe> recipes;

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateRecipeList();
    }

    private void updateRecipeList(){
        recipeFlowPane.getChildren().clear();
        recipes = bc.getRecipes();
        for (Recipe recipe : recipes) {
            RecipeListItem rli = new RecipeListItem(recipe, this);
            recipeFlowPane.getChildren().add(rli);
        }
    }

}