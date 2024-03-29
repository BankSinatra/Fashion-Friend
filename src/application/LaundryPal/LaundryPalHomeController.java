package application.LaundryPal;

import application.LaundryPal.Laundry;
import application.LaundryPal.LaundryMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class LaundryPalHomeController {
	
		@FXML
		private Button btn_wash;

		@FXML
	    private Button btn_wash_30;

	    @FXML
	    private Button btn_wash_40;

	    @FXML
	    private Button btn_wash_50;
	    
	    @FXML
	    private Button btn_wash_60;
	    
	    @FXML
	    private Button btn_hand_wash;
	    
	    @FXML
	    private Button btn_do_not_wash;

		@FXML
		private Button btn_tumble_dry;

		@FXML
		private Button btn_tumble_dry_low;

		@FXML
		private Button btn_tumble_dry_normal;

		@FXML
		private Button btn_do_not_tumble_dry;

	    @FXML
	    private Button btn_hcs_gentle;

	    @FXML
	    private Button btn_dry;

	    @FXML
	    private Button btn_drip_dry_shade;

	    @FXML
	    private Button btn_no_chemical_clean;

	    @FXML
	    private Button btn_dry_shade;

	    @FXML
	    private Button btn_pce_only;

	    @FXML
	    private Button btn_line_dry;

	    @FXML
	    private Button btn_hcs_very_gentle;

	    @FXML
	    private Button btn_dry_flat_shade;

	    @FXML
	    private Button btn_pro_cleaning;

	    @FXML
	    private Button btn_no_wet_clean;

	    @FXML
	    private Button btn_do_not_bleach;

	    @FXML
	    private Button btn_pce_very_gentle;

	    @FXML
	    private Button btn_wet_clean_gentle;

	    @FXML
	    private Button btn_bleach;

	    @FXML
	    private Button btn_wet_clean;

	    @FXML
	    private Button btn_dry_flat;

	    @FXML
	    private Button btn_wet_clean_very_gentle;

	    @FXML
	    private Button btn_bleach_cl;

	    @FXML
	    private Button btn_iron;

	    @FXML
	    private Button btn_line_dry_shade;

	    @FXML
	    private Button btn_iron_high;

	    @FXML
	    private Button btn_drip_dry;

	    @FXML
	    private Button btn_hcs_only;

	    @FXML
	    private Button btn_pce_gentle;

	    @FXML
	    private Button btn_do_not_iron;

	    @FXML
	    private Button btn_iron_low;

	    @FXML
	    private Button btn_bleach_no_cl;

	    @FXML
	    private Button btn_iron_medium;

		@FXML
		public Button btn_return_to_home;

		@FXML
		public VBox results_pane;

		@FXML
		public Button btn_get_laundry_instructions;

		private final ArrayList<Laundry> iconList = new ArrayList<>();

		/**
	 	*
	 	* @param event Javafx's parameter for getting event details
	 	*/
		public void switchToHomeScene (ActionEvent event) {
			Stage stage = (Stage) results_pane.getScene().getWindow();
			stage.close();
		}

	/**
	 * Binds an image to a button and associates an image to it. Also sets styles for it
	 * @param location The location of the image we want to use
	 * @param btn The button we want to bind the image to
	 * @param instructions The laundry instructions related to the button
	 * @throws FileNotFoundException If the location of the image is wrong
	 */
	    private void bindImage(String location, Button btn, String instructions) throws FileNotFoundException {
			FileInputStream file = new FileInputStream(location);
	        Image img = new Image(file);
	        ImageView imageView = new ImageView(img);
	        imageView.setPreserveRatio(true);
	        imageView.setFitWidth(50);
	        imageView.setFitHeight(50);
	        btn.setGraphic(imageView);
	        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btn.setStyle("-fx-background-color: white");
			btn.setOnAction(event -> {
				Object node = event.getSource();
				if (node instanceof Button b) {
					if(b.getParent() instanceof HBox parent) {
						try {
							//There can only be one laundry method present ever
							Laundry la = new Laundry(parent.getId(), instructions);
							for(Laundry l : iconList){
								if (l.getLaundryMethod() == la.getLaundryMethod()){
									iconList.remove(l);
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						for(Node child : parent.getChildren()){
							if(!child.equals(b)){
								child.setStyle("-fx-background-color: white");
							}else{
								//Select the button
								child.setStyle("-fx-background-color: white;"+"-fx-border-color: blue;" );
							}
						}
						try {
							iconList.add(new Laundry(parent.getId(), instructions));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			});
	    }

	/**
	 * Helper function to format the enum name to a neater name
	 * @param string The enum lowercase name
	 * @return The enum name with a capital letter for the first letter and lowercase for the rest
	 */

		private String formatEnum(String string){
			StringBuilder stringBuilder = new StringBuilder();
			char[] s = string.toCharArray();
			for (int i = 0; i < string.length(); i++){
				if(i == 0){
					stringBuilder.append(Character.toUpperCase(s[i]));
				}else if(s[i] == '_'){
					stringBuilder.append(" ");
				}else if(s[i-1] == '_'){
					stringBuilder.append(Character.toUpperCase(s[i]));
				}else{
					stringBuilder.append(s[i]);
				}
			}
			return stringBuilder.toString();
		}

	/**
	 * Calculates which instructions to display. Uses the enum classes to categorize in the enum declaration order
	 */
	@FXML
		public void calculateLaundry(){
			//Create the layout and inject it into the vbox pane
			results_pane.getChildren().clear();

			Comparator<Laundry> laundryComparator = new Comparator<Laundry>() {
				@Override
				public int compare(Laundry o1, Laundry o2) {
						LaundryMethod a = o1.getLaundryMethod();
						LaundryMethod b = o2.getLaundryMethod();
						return a.compareTo(b);
				}

			};
			iconList.sort(laundryComparator);

			if(iconList.size() > 0){
				for (Laundry laundry : iconList){
					String headingText = formatEnum(laundry.getLaundryMethod().toString().toLowerCase(Locale.ROOT));
					Label heading = new Label(headingText);
					heading.setFont(new Font("System", 22));

					String subTitleText = laundry.getInstructions();
					Label subTitle = new Label(subTitleText);
					results_pane.getChildren().add(heading);
					results_pane.getChildren().add(subTitle);
				}
			}else{
				Label noIconsSelected = new Label("Please select an icon to have laundry instructions show up");
				noIconsSelected.setFont(new Font("System", 22));
				results_pane.getChildren().add(noIconsSelected);
			}

		}

	/**
	 * This just binds all the buttons to a certain image and instruction
	 */
	public void initialize() {
	        try{
				//Washing
	            bindImage("Resources/LaundrySymbols/Washing/Wash.svg.png", btn_wash, "Wash your article of clothing in water");
	            bindImage("Resources/LaundrySymbols/Washing/Wash_30.svg.png", btn_wash_30, "Wash your clothing in water at or below 30 °C");
	            bindImage("Resources/LaundrySymbols/Washing/Wash_40.svg.png", btn_wash_40, "Wash your clothing in water at or below 40 °C");
	            bindImage("Resources/LaundrySymbols/Washing/Wash_50.svg.png", btn_wash_50, "Wash your clothing in water at or below 50 °C");
	            bindImage("Resources/LaundrySymbols/Washing/Wash_60.svg.png", btn_wash_60, "Wash your clothing in water at or below 60 °C");
	            bindImage("Resources/LaundrySymbols/Washing/Handwash.svg.png", btn_hand_wash, "Hand wash your clothing");
	            bindImage("Resources/LaundrySymbols/Washing/No_Wash.svg.png", btn_do_not_wash, "Do not wash your clothing in water");

				//Tumble Dry
				bindImage("Resources/LaundrySymbols/Drying/Tumble/Tumble_Dry.svg.png", btn_tumble_dry, "You can machine dry your clothing");
				bindImage("Resources/LaundrySymbols/Drying/Tumble/Tumble_Dry_Low.svg.png", btn_tumble_dry_low, "Machine dry your clothing at a low temperature");
				bindImage("Resources/LaundrySymbols/Drying/Tumble/Tumble_Dry_High.svg.png", btn_tumble_dry_normal, "Machine dry your clothing at a normal temperature");
				bindImage("Resources/LaundrySymbols/Drying/Tumble/No_Tumble_Dry.svg.png", btn_do_not_tumble_dry, "Do not machine dry your clothing");

				//Natural Dry
				bindImage("Resources/LaundrySymbols/Drying/Natural/Dry.svg.png", btn_dry, "You can dry your clothing");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Line_Dry.svg.png", btn_line_dry, "Line dry your clothing");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Dry_Flat.svg.png", btn_dry_flat, "Dry your clothing on a flat surface");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Drip_Dry.svg.png", btn_drip_dry, "Drip dry your clothing");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Dry_Shade.svg.png", btn_dry_shade, "Dry your clothing in the shade");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Line_Dry_Shade.svg.png", btn_line_dry_shade, "Line dry your clothing in the shade");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Dry_Flat_Shade.svg.png", btn_dry_flat_shade, "Dry your clothing on a flat surface in the shade");
				bindImage("Resources/LaundrySymbols/Drying/Natural/Drip_Dry_Shade.svg.png", btn_drip_dry_shade, "Drip dry your clothing in the shade");

				//Iron
				bindImage("Resources/LaundrySymbols/Ironing/Iron.svg.png", btn_iron, "You can iron your clothing");
				bindImage("Resources/LaundrySymbols/Ironing/Iron_Low.svg.png", btn_iron_low, "You can iron your clothing at the low setting");
				bindImage("Resources/LaundrySymbols/Ironing/Iron_Medium.svg.png", btn_iron_medium, "You can iron your clothing at the medium setting");
				bindImage("Resources/LaundrySymbols/Ironing/Iron_High.svg.png", btn_iron_high, "You can iron your clothing at the high setting");
				bindImage("Resources/LaundrySymbols/Ironing/No_Iron.svg.png", btn_do_not_iron, "Do not iron this clothing");

				//Dry Cleaning
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/ProCleaning.svg.png", btn_pro_cleaning, "You can professionally clean your clothing");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/HCS.svg.png", btn_hcs_only, "When dry cleaning, use hydrocarbon solvents (HCS) only");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/HCS_Gentle.svg.png", btn_hcs_gentle, "When dry cleaning, use hydrocarbon solvents (HCS) only. Make sure you are cleaning gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/HCS_Very_Gentle.svg.png", btn_hcs_very_gentle, "When dry cleaning, use hydrocarbon solvents (HCS) only. Make sure you are cleaning very gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/PCE.svg.png", btn_pce_only,"When dry cleaning, use perchloroethylene(PCE) only");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/PCE_Gentle.svg.png", btn_pce_gentle,"When dry cleaning, use perchloroethylene(PCE) only. Make sure you are cleaning gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/PCE_Very_Gentle.svg.png", btn_pce_very_gentle, "When dry cleaning, use perchloroethylene(PCE) only. Make sure you are cleaning very gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/DryCleaning/No_Chemical_Cleaning.svg.png", btn_no_chemical_clean, "Do not chemical clean this clothing");

				//Wet Cleaning
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/WetCleaning/Wet_Clean.svg.png", btn_wet_clean, "Wet clean your clothing");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/WetCleaning/Wet_Clean_Gentle.svg.png", btn_wet_clean_gentle,"Wet clean your clothing gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/WetCleaning/Wet_Clean_Very_Gentle.svg.png", btn_wet_clean_very_gentle, "Wet clean your clothing very gently");
				bindImage("Resources/LaundrySymbols/ProfessionalCleaning/WetCleaning/No_Wet_Clean.svg.png", btn_no_wet_clean, "Do not wet clean this clothing");

				//Bleach
				bindImage("Resources/LaundrySymbols/Bleaching/Bleaching.svg.png", btn_bleach, "You can bleach your clothing");
				bindImage("Resources/LaundrySymbols/Bleaching/Bleaching_Cl.svg.png", btn_bleach_cl, "Bleach your clothing with chlorine bleach");
				bindImage("Resources/LaundrySymbols/Bleaching/Bleaching_No_Cl.svg.png", btn_bleach_no_cl, "Bleach your clothing with non-chlorine bleach");
				bindImage("Resources/LaundrySymbols/Bleaching/No_Bleaching.svg.png", btn_do_not_bleach, "Do not bleach this clothing");

	        }catch(Exception e){
	            System.out.println("Oh no");
	        }
	    }

}
