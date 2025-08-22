/*

    UI Navigation

*/

// Calendar
export function nextMonth(month, year){
    month+=1;
    if (month>11){
        month = 0;
        year +=1;
    }
    return {month, year}
}
export function prevMonth(month, year){
    month-=1;
    if (month<0){
        month = 11;
        year -=1;
    }
    return {month, year}
}
export function addCellHover(cell){
    let scrollInterval;

    cell.addEventListener('mouseenter', () => {
        // Start auto-scrolling down
        scrollInterval = setInterval(() => {
            // Scroll by 1px every 10ms (adjust speed as needed)
            cell.scrollTop += 2;
            // Stop if reached the bottom
            if (cell.scrollTop + cell.clientHeight >= cell.scrollHeight) {
                clearInterval(scrollInterval);
            }
        }, 10);
    });

    cell.addEventListener('mouseleave', () => {
        // Stop scrolling and reset to top
        clearInterval(scrollInterval);
        scrollInterval = setInterval(() => {
            // Scroll by 1px every 10ms (adjust speed as needed)
            cell.scrollTop -= 2;
            // Stop if reached the bottom
            if (cell.scrollTop ==0) {
                clearInterval(scrollInterval);
            }
        }, 10);
    });
}

// Sidebar Navigation
const sidebar = document.getElementById('sidebar-body');
const calendar = document.getElementById('calendar-view');
const savedView = document.getElementById('saved-items');
const dayView = document.getElementById('my-day');

export function toggleSaved(){
    if (sidebar.classList.contains('hidden')){
        // switch view
        savedView.classList.remove('hidden');
        dayView.classList.add('hidden');
        //show sidebar
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebar-hidden');
    }else if(!dayView.classList.contains('hidden')){
        savedView.classList.remove('hidden');
        dayView.classList.add('hidden');
    }else{
        sidebarCollapse();
    }
}
export function toggleAgenda(){
    if (sidebar.classList.contains('hidden')){
        // switch view
        dayView.classList.remove('hidden');
        savedView.classList.add('hidden');
        //show sidebar
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebarhidden');
    }else if(!savedView.classList.contains('hidden')){
        dayView.classList.remove('hidden');
        savedView.classList.add('hidden');
    }else{
        sidebarCollapse();
    }
}
export function sidebarCollapse(){
    sidebar.classList.add('hidden');
}

// Day View Navigaion

// Saved Items Navigation

export function toggleTaskDrawer(){
    var parent = document.getElementById("tasks-drawer");
    var tasksView = document.getElementById("saved-tasks");
    if(parent.classList.contains('open')){
        parent.classList.remove('open');
        tasksView.classList.remove('open');
    }else{
        parent.classList.add('open');
        tasksView.classList.add('open');
    }
}

export function toggleTemplateDrawer(){
    var parent = document.getElementById("templates-drawer");
    var templatesView = document.getElementById("saved-templates");
    if(parent.classList.contains('open')){
        parent.classList.remove('open');
        templatesView.classList.remove('open');
    }else{
        parent.classList.add('open');
        templatesView.classList.add('open');
    }
}