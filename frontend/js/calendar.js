// INIT

// populate calendar
const today = new Date();
generateCalendar(today.getMonth(), today.getFullYear(), today.getDate());

// populate saved tasks and templates

// populate day view


// Calendar get dates

function generateCalendar(month, year, today) {
    const grid = document.getElementById("calendar-grid");
    grid.innerHTML = ""; // clear previous
  
    const firstDay = new Date(year, month, 1).getDay(); // 0 = Sunday
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const monthNames = [
        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    ];
    const monthYearHeader = document.getElementById("month-year");
    if (monthYearHeader) {
        monthYearHeader.textContent = `${monthNames[month]} ${year}`;
    }

    var totalCells = 0;
    var sunday = 0;
    if (firstDay !== sunday){
        const lastMonth = new Date(year, month, 0).getDate();
        sunday= lastMonth-(firstDay-1);
    }
  
    // Add empty cells before the first day
    for (let i = 0; i < firstDay; i++) {
      grid.innerHTML += `<div class="day-cell disabled"><h5>${sunday}</h5></div>`;
      totalCells++;
      sunday++;
    }
  
    // Add actual day cells
    for (let day = 1; day <= daysInMonth; day++) {
        //REPLACE WITH DOM
        if (today===day){
            grid.innerHTML += `
            <div class="day-cell">
                <h5 class="today"><span>${day}</span></h5></div>`;
        }else{
            grid.innerHTML += `
            <div class="day-cell">
                <h5>${day}</h5>
                <div class= "calendar-task">Task 1</div>
                <div class= "calendar-template">Template 1</div>
                <div class= "calendar-task done">Task 2</div>
                <div class= "calendar-template done">Template 2</div>
                </div>`;
        }
      
      totalCells++;
    }

    var days = 1;

    while (totalCells<(7*6)){
        grid.innerHTML += `<div class="day-cell disabled"><h5>${days}</h5></div>`;
        totalCells++;
        days++;
    }
  }

//   month change view logic
  var month = today.getMonth();
  var year = today.getFullYear();
  document.getElementById('next-month').addEventListener("click", ()=>{
    month += 1
    document.getElementById("calendar-grid").innerHTML = "";
    if ((month === today.getMonth()) && (year === today.getFullYear())){
        generateCalendar(month, year, today.getDate());
    }else if(month > 11){
        month = 0;
        year +=1;
        generateCalendar(month, year);
    }else{
        generateCalendar(month, year);
    }
  });

  document.getElementById('prev-month').addEventListener("click", ()=>{
    month -= 1
    document.getElementById("calendar-grid").innerHTML = "";
    if ((month === today.getMonth()) && (year === today.getFullYear())){
        generateCalendar(month, year, today.getDate());
    }else if(month < 0){
        month = 11;
        year -=1;
        generateCalendar(month, year);
    }else{
        generateCalendar(month, year);
    }
  });

  // Sidebar change view logic

  const sidebar = document.getElementById('sidebar-body');
  const calendar = document.getElementById('calendar-view');
  const savedView = document.getElementById('saved-items');
  const dayView = document.getElementById('my-day');
  
  document.getElementById("saved-view").addEventListener("click", ()=>{
    if (sidebar.classList.contains('hidden')){
        // switch view
        savedView.classList.remove('hidden');
        dayView.classList.add('hidden');
        //show sidebar
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebar-hidden');
        console.log('Sidebar is hidden. Showing now')
    }else if(!dayView.classList.contains('hidden')){
        savedView.classList.remove('hidden');
        dayView.classList.add('hidden');
    }else{
        sidebar.classList.add('hidden');
        console.log('Sidebar is showing. Hiding now')
    }
  });

    document.getElementById("day-view").addEventListener("click", ()=>{
    if (sidebar.classList.contains('hidden')){
        // switch view
        dayView.classList.remove('hidden');
        savedView.classList.add('hidden');
        //show sidebar
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebarhidden');
        console.log('Sidebar is hidden. Showing now')
    }else if(!savedView.classList.contains('hidden')){
        dayView.classList.remove('hidden');
        savedView.classList.add('hidden');
    }else{
        sidebar.classList.add('hidden');
        console.log('Sidebar is showing. Hiding now')
    }
  });

  document.getElementById("collapse").addEventListener("click", ()=>{
    sidebar.classList.add('hidden');
    console.log('Hiding Sidebar');
  });
  // Task and Template View Logic

document.getElementById("task-toggle").addEventListener("click", ()=>{
    var parent = document.getElementById("tasks-drawer");
    var tasksView = document.getElementById("saved-tasks");
    if(parent.classList.contains('open')){
        parent.classList.remove('open');
        tasksView.classList.remove('open');
    }else{
        parent.classList.add('open');
        tasksView.classList.add('open');
    }
});

document.getElementById("template-toggle").addEventListener("click", ()=>{
    var parent = document.getElementById("templates-drawer");
    var templatesView = document.getElementById("saved-templates");
    if(parent.classList.contains('open')){
        parent.classList.remove('open');
        templatesView.classList.remove('open');
    }else{
        parent.classList.add('open');
        templatesView.classList.add('open');
    }
});

document.querySelectorAll('.day-cell').forEach(cell => {
    let scrollInterval;

    cell.addEventListener('mouseenter', () => {
        // Start auto-scrolling down
        scrollInterval = setInterval(() => {
            // Scroll by 1px every 10ms (adjust speed as needed)
            cell.scrollTop += 1;
            // Stop if reached the bottom
            if (cell.scrollTop + cell.clientHeight >= cell.scrollHeight) {
                clearInterval(scrollInterval);
            }
        }, 10);
    });

    cell.addEventListener('mouseleave', () => {
        // Stop scrolling and reset to top
        clearInterval(scrollInterval);
        cell.scrollTop = 0;
    });
});
