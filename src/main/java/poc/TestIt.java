//package poc;
//import javax.management.BadStringOperationException;
//
//import components.PageDialogFactory;
//
//public class TestIt {
//
//	public static void main(String[] args) throws BadStringOperationException {
//		
//		// SinglePageDialog; String:name; String:nick name, min:5, max:15, allow-spaces:false;
//		// Number:Id, min:5, max:110; Boolean:Working?; SingleOption:Favourite color, inputs:red|blue|green|yellow;
//		// MultiOptions:Country, inputs:New Zealand|Russia|Island|Iraq;
//		
//		String text = "SinglePageDialog; String:name; String:nick name, min:5, max:15, allow-spaces:false;" +
//				"Number:Age, min:5, max:110; Boolean:Working?; SingleOption:Favourite color, inputs:red|blue|green|yellow;" +
//				"SingleOption:Favourite animal, inputs:dog|cat|mouse|dinasor, style:dropdown;"+
//				"MultiOptions:Country, inputs:New Zealand|Russia|Island|Iraq;";
//		
//		PageDialogFactory dialogFactory = new PageDialogFactory();
//		dialogFactory.getDialog(text).generate();
//
//	}
//
//}
