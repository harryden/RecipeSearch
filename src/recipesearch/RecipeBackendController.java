package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RecipeBackendController {

    private final String[] VALID_CUISINES = {"Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike"};
    private final String[] VALID_INGREDIENTS = {"Kött", "Fisk", "Kyckling", "Vegetarisk"};
    private final String[] VALID_DIFFICULTY = {"Kött", "Fisk", "Kyckling", "Vegetarisk"};
    private final int[] VALID_TIME = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150};

    //private final Set<String> VALID_CUISINES = Set.of("Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
    //private final Set<String> VALID_INGREDIENTS = Set.of("Kött", "Fisk", "Kyckling", "Vegetarisk");
    //private final Set<String> VALID_DIFFICULTY = Set.of("Lätt", "Mellan", "Svår");
    //private final Set<Integer> VALID_TIME = Set.of(10,20,30,40,50,60,70,80,90,100,110,120,130,140,150);

    RecipeDatabase db = RecipeDatabase.getSharedInstance();

    String cuisine;
    String mainIngredient;
    String difficulty;
    int maxPrice;
    int maxTime;

    public List<Recipe> getRecipes(){
        return db.search(new SearchFilter(difficulty, maxTime, cuisine, maxPrice, mainIngredient));
    }

    public void setCuisine(String cuisine) {
        boolean found = false;
        for (String cuisines : VALID_CUISINES){
            if (cuisines == cuisine){
                found = true;
            }
        }
        if (found)
            this.cuisine = cuisine;
        else
            this.cuisine = null;
    }

    public void setMainIngredient(String mainIngredient){
        boolean found = false;
        for (String ingredients : VALID_INGREDIENTS){
            if (ingredients == mainIngredient){
                found = true;
            }
        }
        if (found)
            this.mainIngredient = mainIngredient;
        else
            this.mainIngredient = null;
    }

    public void setDifficulty(String difficulty){
        boolean found = false;
        for (String difficulties : VALID_DIFFICULTY){
            if (difficulties == difficulty){
                found = true;
            }
        }
        if (found)
            this.difficulty = difficulty;
        else
            this.difficulty = null;
    }

    public void setMaxPrice(int maxPrice){
        this.maxPrice = Math.max(maxPrice, 0);
    }
    public void setMaxTime(int maxTime){
        boolean found = false;
        for (int maxTimes : VALID_TIME){
            if (maxTimes == maxTime){
                found = true;
            }
        }
        if (found)
            this.maxTime = maxTime;
        else
            this.maxTime = 0;
    }

}
