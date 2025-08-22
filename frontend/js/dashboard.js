/*

    Global Listener and Initial population

*/

// IMPORTS
import {generateCalendar, populateSavedTasks, populateSavedTemplates, populateDayView} from "./population.js";


// INITIAL POPULATION

// calendar
const today = new Date();
generateCalendar(today.getMonth(), today.getFullYear(), today.getDate());
// saved tasks and templates
populateSavedTasks();
populateSavedTemplates();
// populate day view
populateDayView(today);




// GLOBAL LISTENER
document.addEventListener("click", function (event) {
    // format:
    //
    //     if (event.target.matches(/*CLASS OR ID IN QUOTES*/)) {
    //         // if dynamic element
    //
    //         const ID = event.target.dataset./*ID FORMAT*/;
    //         const template = document.getElementById(/*id format ${ID}*/);
    //        
    //         //  logic here
    //     }
    //
    

    // Sidebar Buttons



    // Day View / Agenda


    
    // Saved Items View



    // Calendar View

});