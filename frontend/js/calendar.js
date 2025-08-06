// INIT

// populate calendar
const today = new Date();
generateCalendar(today.getMonth(), today.getFullYear(), today.getDate());

// populate saved tasks and templates
populateSavedTasks();
populateSavedTemplates();

// populate day view
populateDayView(today);

// LOGIC and EVENT LISTENERS

// Month navigation logic
  var month = today.getMonth();
  var year = today.getFullYear();
  document.getElementById('next-month').addEventListener("click", ()=>{
    month += 1
    document.getElementById("calendar-grid").innerHTML = "";
    if ((month === today.getMonth()) && (year === today.getFullYear())){ 
        // if current month, generate calendar with today's date highlighted
        generateCalendar(month, year, today.getDate());
    }else if(month > 11){
        // if month exceeds December, reset to January and increment year
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
        // if current month, generate calendar with today's date highlighted
        generateCalendar(month, year, today.getDate());
    }else if(month < 0){
        // if month is less than January, reset to December and decrement year
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
    }else if(!dayView.classList.contains('hidden')){
        savedView.classList.remove('hidden');
        dayView.classList.add('hidden');
    }else{
        sidebar.classList.add('hidden');
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
    }else if(!savedView.classList.contains('hidden')){
        dayView.classList.remove('hidden');
        savedView.classList.add('hidden');
    }else{
        sidebar.classList.add('hidden');
    }
  });

  document.getElementById("collapse").addEventListener("click", ()=>{
    sidebar.classList.add('hidden');
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
        cell.scrollTop = 0;
    });
});

// Generate the calendar for the current month and year
// The function takes the month, year, and today's date as parameters
// Today can be null if not current month
function generateCalendar(month, year, today) {
    const grid = document.getElementById("calendar-grid"); // Get the calendar grid element
    grid.innerHTML = ""; // clear previous content
  
    const firstDay = new Date(year, month, 1).getDay(); // 0 = Sunday
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const monthNames = [
        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    ];
    const monthYearHeader = document.getElementById("month-year");
    if (monthYearHeader) {
        monthYearHeader.textContent = `${monthNames[month]} ${year}`; // Set month and year in header
    }

    var totalCells = 0;
    var sunday = 0;
    if (firstDay !== sunday){ // check if first day is not Sunday; calculate the last Sunday of the previous month
        const lastMonth = new Date(year, month, 0).getDate();
        sunday= lastMonth-(firstDay-1);
    }
  
    // Add empty cells before the first day
    for (let i = 0; i < firstDay; i++) {
        grid.appendChild(createCalendarCell(sunday, [], [], 'disabled'));
        totalCells++;
        sunday++;
    }
  
    // Add normal day cells
    for (let day = 1; day <= daysInMonth; day++) {
        //TODO: get JSON data for tasks and templates
        if (today===day){
            grid.appendChild(createCalendarCell(day,  [{ name: 'Task 1', done: false }, { name: 'Task 2', done: true }], 
                    [{ name: 'Template 1', done: false }, { name: 'Template 2', done: true }], 'today'));
        }else{
            grid.appendChild(createCalendarCell(day, 
                    [{ name: 'Task 1', done: false }, { name: 'Task 2', done: true }], 
                    [{ name: 'Template 1', done: false }, { name: 'Template 2', done: true }],
                    'normal'));
        }
      
      totalCells++;
    }

    var days = 1;

    while ((totalCells % 7) != 0){ // check if the last row is not full; add empty cells until it is full
        grid.appendChild(createCalendarCell(days, [], [], 'disabled'));
        totalCells++;
        days++;
    }
  }
// Create a calendar cell with the given day number, tasks, templates, and status
function createCalendarCell(dayNumber, tasks, templates, status = 'normal') {
    // Get the template
    const template = document.getElementById('calendarCell');
    
    // Clone and modify the template content
    const clone = template.content.cloneNode(true);
    const dayCell = clone.querySelector('.day-cell'); 
    //TODO: add id based on date

    // Add the day number
    const dayNumberElement = document.createElement('h5');
    if (status === 'today') {
        dayNumberElement.className = 'today';
        const todaySpan = document.createElement('span');
        todaySpan.textContent = dayNumber;  
        dayNumberElement.appendChild(todaySpan);
    }else{
        dayNumberElement.textContent = dayNumber;
    }
    dayCell.appendChild(dayNumberElement);
    
    // add content based on status
    if(status === 'disabled') {
        dayCell.classList.add('disabled');
    }else{
        //add tasks                 
        tasks.forEach(task => {
            const taskElement = document.createElement('div');
            taskElement.className = task.done ? 'calendar-task done' : 'calendar-task';
            taskElement.textContent = task.name;
            dayCell.appendChild(taskElement);
        });
        
        // Add templates
        templates.forEach(template => {
            const templateElement = document.createElement('div');
            templateElement.className = template.done ? 'calendar-template done' : 'calendar-template';
            templateElement.textContent = template.name;
            dayCell.appendChild(templateElement);
        });
    }
    
    // Return the modified cell
    return clone;
}
function populateSavedTasks(){
    // This function will populate the saved tasks section
    // get the saved task template
    const taskTemplate = document.getElementById('savedTask');

    //get the saved tasks container
    const savedTasksContainer = document.getElementById('saved-tasks');

    //get new task button
    const newTask = document.getElementById('new-saved-task');
    //TODO: API request to get saved tasks

    for (let i = 1 ; i <= 7; i++) { // Example: populate with 5 saved tasks
        // clone and modify the template content
        const clone = taskTemplate.content.cloneNode(true); 
        const savedTaskItem = clone.querySelector('.saved-task-item');

        savedTaskItem.textContent = `Saved Task ${i}`; // Set the text content 
        // TODO: replace with JSON data
        
        savedTasksContainer.insertBefore(clone, newTask); // Append the cloned item to the container
    }
}
function populateSavedTemplates(){
    //TODO: get data from API

    // get template
    const savedTemplateTemp = document.getElementById('savedTemplate');

    //get Tasks Template
    const savedTempTask = document.getElementById('savedTemplateTask');

    // get saved template container
    const savedTemplateContainer = document.getElementById('saved-templates');
    
    // get new template option
    const newTemplate = document.getElementById('new-template');


    for (let i = 1; i<6; i++){
        // set up number of template tasks for demo
        let numTasks = Math.floor(Math.random() * 10) + 1;

        // clone and modify template content
        const templateClone = savedTemplateTemp.content.cloneNode(true);
        const savedTaskHead = templateClone.querySelector('.saved-template-item-head')

        // add template name and task count
        const templateName = document.createElement('H4');
        templateName.textContent = `Template ${i}`;
        //TODO: populate based on API data

        const taskCount = document.createElement('H4');
        taskCount.textContent = `${numTasks}/10`;

        savedTaskHead.appendChild(templateName);
        savedTaskHead.appendChild(taskCount);

        // get template body
        const templateBody = templateClone.querySelector('.saved-template-item-body')

        for(let j = 1; j<=numTasks; j++){
            const taskClone = savedTempTask.content.cloneNode(true);
            const templateTask = taskClone.querySelector('.saved-template-task');
            templateTask.textContent = `Template Task ${j}`;
            templateBody.appendChild(taskClone);
        }

        // add template to container
        savedTemplateContainer.insertBefore(templateClone, newTemplate);
    }
}
function populateDayView(date){
    //TODO: get all templates and tasks by date

    let numTasks = Math.floor(Math.random() * 5) + 1;
    let numTemplates = Math.floor(Math.random() * 5) + 1;

    //get day view task template
    const dayTask = document.getElementById('dayViewTask');
    //get day view template
    const dayTemplate = document.getElementById('dayViewTemplate');
    //get day view template task
    const dayTempTask = document.getElementById('dayViewTemplateTask');
    //get container 
    const dayViewContainer = document.getElementById('my-day');

    //get day view new task
     const dayViewNewTask = document.getElementById('day-view-new-task');

    //TODO replace for to foreach
    // put all tasks
    for(let i=1; i<=numTasks; i++){
        const taskClone = dayTask.content.cloneNode(true);

        // TODO: label task id based on DB id
        const task = taskClone.querySelector('.task-item');
        task.id = `task-${i}`;

        // change label based on id
        const checkbox = taskClone.querySelector('#taskdone')
        checkbox.id = `task-done-${i}`;

        const taskLabel = taskClone.querySelector('.checkbox-label');
        taskLabel.setAttribute('for', checkbox.id);
        const taskName = document.createElement('p');
        taskName.textContent = `Task ${i}`;

        taskLabel.appendChild(taskName);
        dayViewContainer.insertBefore(taskClone, dayViewNewTask);
    }

    // put all templates
    for(let i=1; i<=numTemplates; i++){
        const templateClone = dayTemplate.content.cloneNode(true);
        
        // TODO: label task id based on DB id
        const template = templateClone.querySelector('.template-item-head');
        template.id = `template-${i}`;
        template.textContent = `Template ${i}`;

        templateBody = templateClone.querySelector('.template-item-body');

        
        for(let j=1; j<= 3; j++){

            const taskClone = dayTempTask.content.cloneNode(true);

            const task = taskClone.querySelector('.template-task-item');
            task.id = `task-${j}-${i}`;

            // change label based on id
            const checkbox = taskClone.querySelector('#taskdone1')
            checkbox.id = `temp-task-done-${j}-${i}`;

            const taskLabel = taskClone.querySelector('.checkbox-label');
            taskLabel.setAttribute('for', checkbox.id);

            const taskName = document.createElement('p');
            taskName.textContent = `Template Task ${j}`;

            taskLabel.appendChild(taskName);
            templateBody.appendChild(taskClone);
        }

        dayViewContainer.insertBefore(templateClone, dayViewNewTask);
    }
}