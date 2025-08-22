/*

    Global Listener and Initial population

*/

// IMPORTS
import {generateCalendar, populateSavedTasks, populateSavedTemplates, populateDayView} from "./population.js";
import {nextMonth, prevMonth, toggleSaved, toggleAgenda, sidebarCollapse, addCellHover, toggleTaskDrawer, toggleTemplateDrawer} from "./navigation.js";

// INITIAL POPULATION

// calendar
const today = new Date();
var month = today.getMonth();
var year = today.getFullYear();
generateCalendar(today.getMonth(), today.getFullYear(), today.getDate());
// saved tasks and templates
populateSavedTasks();
populateSavedTemplates();
// populate day view
populateDayView(today);

// add event listener to each day cell
document.querySelectorAll('.day-cell').forEach(cell => {
    addCellHover(cell);
});



// GLOBAL LISTENER
document.addEventListener("click", function (event) {
    // format:
    //
    //     if (event.target.matches(/*CLASS OR ID IN QUOTES*/)) { // for exact element
    //     if (event.target.closest(/*CLASS OR ID IN QUOTES*/)) { // for element and children
    //         // if dynamic element
    //
    //         const ID = event.target.dataset./*ID FORMAT*/;
    //         OR
    //         const element = event.target 
    //        
    //         //  logic here
    //     }
    //
    

    // Sidebar Buttons

    if (event.target.closest("#saved-view")) {
        toggleSaved();
    }
    if (event.target.closest("#day-view")) {
        toggleAgenda();
    }
    if (event.target.closest("#collapse")) {
        sidebarCollapse();
    }

    // Day View / Agenda

    
    // Saved Items View

    if (event.target.closest("#tasks-drawer")) {
        toggleTaskDrawer();
    }

    if (event.target.closest("#templates-drawer")) {
        toggleTemplateDrawer();
    }

    // Calendar View
    
    // Next Month
    if (event.target.closest("#next-month")) {
        ({ month, year } = nextMonth(month, year));
        generateCalendar(month, year, isThisMonth(month, year));
    }

    // Previous Month
    if (event.target.closest("#prev-month")) {
        ({ month, year } = prevMonth(month, year));
        generateCalendar(month, year, isThisMonth(month, year));
    }

});

function isThisMonth(month, year){
    if ((month === today.getMonth()) && (year === today.getFullYear())){
        return today.getDate();
    }else{
        return undefined;
    }
}