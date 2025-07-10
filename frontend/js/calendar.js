function generateCalendar(month, year, today) {
    const grid = document.getElementById("calendar-grid");
    grid.innerHTML = ""; // clear previous
  
    const firstDay = new Date(year, month, 1).getDay(); // 0 = Sunday
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    var totalCells = 0;
  
    // Add empty cells before the first day
    for (let i = 0; i < firstDay; i++) {
      grid.innerHTML += `<div class="day-cell empty"></div>`;
      totalCells++;
    }
  
    // Add actual day cells
    for (let day = 1; day <= daysInMonth; day++) {
        //REPLACE WITH DOM
        if (today===day){
            grid.innerHTML += `
            <div class="day-cell">
                <h5 class="calendar-date today"><span>${day}</span></h5>
            </div>`;
            console.log(`today is ${today}`)
        }else{
            grid.innerHTML += `
            <div class="day-cell">
                <h5 class="calendar-date">${day}</h5>
            </div>`;
        }
      
      totalCells++;
    }

    while (totalCells<(7*6)){
        grid.innerHTML += `<div class="day-cell empty"></div>`;
        totalCells++;
    }
  }
  
  const today = new Date();
  generateCalendar(today.getMonth(), today.getFullYear(), today.getDate());

  const sidebar = document.getElementById('sidebar-body');
  const calendar = document.getElementById('calendar-view');

  
  document.getElementById("saved-items").addEventListener("click", ()=>{
    if (sidebar.classList.contains('hidden')){
        //add  saved items view is visible
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebar-hidden');
        console.log('Sidebar is hidden. Showing now')

    }else{
        sidebar.classList.add('hidden');
        console.log('Sidebar is showing. Hiding now')
    }
  });

    document.getElementById("day-view").addEventListener("click", ()=>{
    if (sidebar.classList.contains('hidden')){
        //add  saved items view is visible
        sidebar.classList.remove('hidden');
        calendar.classList.remove('sidebarhidden');
        console.log('Sidebar is hidden. Showing now')

    }else{
        sidebar.classList.add('hidden');
        console.log('Sidebar is showing. Hiding now')
    }
  });