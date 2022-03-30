
package recipesearch;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.javafx.collections.MappingChange;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    @FXML FlowPane recipeFlowPane;
    @FXML ComboBox mainIngredientComboBox;
    @FXML ComboBox cuisineComboBox;
    @FXML RadioButton allDifficultyRadioButton;
    @FXML RadioButton easyDifficultyRadioButton;
    @FXML RadioButton mediumDifficultyRadioButton;
    @FXML RadioButton hardDifficultyRadioButton;
    @FXML Spinner maxPriceSpinner;
    @FXML Slider maxTimeSlider;
    @FXML Label maxTimeLabel;
    @FXML ImageView detailedViewRecipeImageView;
    @FXML Label detailedViewRecipeLabel;
    @FXML Button detailedViewCloseButton;
    @FXML AnchorPane recipeDetailPane;
    @FXML SplitPane searchPane;

    private RecipeBackendController backendController = new RecipeBackendController();
    private List<Recipe> recipes;

    RecipeDatabase db = RecipeDatabase.getSharedInstance();

    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Recipe recipe : backendController.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }

        updateRecipeList();

        mainIngredientComboBox.getItems().addAll("Visa alla", "Kött", "Fisk", "Kyckling", "Vegetarisk");
        mainIngredientComboBox.getSelectionModel().select("Visa alla");
        mainIngredientComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

        cuisineComboBox.getItems().addAll("Visa alla", "Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
        cuisineComboBox.getSelectionModel().select("Visa alla");
        cuisineComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backendController.setCuisine(newValue);
                updateRecipeList();
            }
        });

        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        allDifficultyRadioButton.setToggleGroup(difficultyToggleGroup);
        easyDifficultyRadioButton.setToggleGroup(difficultyToggleGroup);
        mediumDifficultyRadioButton.setToggleGroup(difficultyToggleGroup);
        hardDifficultyRadioButton.setToggleGroup(difficultyToggleGroup);
        allDifficultyRadioButton.setSelected(true);

        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    backendController.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10,150,50,10); //TODO fundera på om det ska ändras
        maxPriceSpinner.setValueFactory(valueFactory);
        maxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                backendController.setMaxPrice( (int) maxPriceSpinner.getValue() );
                updateRecipeList();
            }
        });

        maxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    Integer value = Integer.valueOf(maxPriceSpinner.getEditor().getText());
                    backendController.setMaxPrice(value);
                    updateRecipeList();
                }
            }
        });

        maxTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int newMaxTime = (int) maxTimeSlider.getValue();
                if(newValue != null && !newValue.equals(oldValue) && !maxTimeSlider.isValueChanging()) {
                    backendController.setMaxTime( newMaxTime );
                }
                maxTimeLabel.setText( Integer.toString(newMaxTime) + " minuter" );
                updateRecipeList();
            }
        });

    }

    void populateRecipeDetailView(Recipe recipe){
        detailedViewRecipeLabel.setText(recipe.getName());
        detailedViewRecipeImageView.setImage(recipe.getFXImage());
    }

    @FXML
    public void closeRecipeView(){
        searchPane.toFront();
    }

    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipeDetailPane.toFront();
    }

    private void updateRecipeList(){
        recipeFlowPane.getChildren().clear();
        recipes = backendController.getRecipes();
        for (Recipe recipe : recipes) {
            RecipeListItem rli = recipeListItemMap.get(recipe.getName());
            recipeFlowPane.getChildren().add(rli);
        }
    }

}