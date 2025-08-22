/*

    Populate dynamic elements
    [ MOCK DATA FOR NOW ]

*/ 

// Day View / Agenda
export function populateDayView(date){
    let numTasks = Math.floor(Math.random() * 5) + 1;
    let numTemplates = Math.floor(Math.random() * 5) + 1;
    const dateLabel = document.getElementById('date');
    dateLabel.textContent = setDateLabel(date);

    //get templates
    const dayTask = document.getElementById('dayViewTask');
    const dayTemplate = document.getElementById('dayViewTemplate');
    const dayTempTask = document.getElementById('dayViewTemplateTask');
    const dayViewContainer = document.getElementById('my-day');
    const dayViewNewTask = document.getElementById('day-view-new-task');

    //TODO: API call to get all templates and tasks by date param
    // put all tasks
    for(let i=1; i<=numTasks; i++){
        const taskClone = dayTask.content.cloneNode(true);

        const task = taskClone.querySelector('.task-item');
        task.id = `task-${i}`;
        // TODO: set id based on format task-[db id]

        // change label based on id
        const checkbox = taskClone.querySelector('#taskdone')
        checkbox.id = `task-done-${i}`;
        // TODO: set id based on format task-done-[db id]

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
        
        //TODO: set id based on format template-[db id]
        const template = templateClone.querySelector('.template-item-head');
        template.id = `template-${i}`;
        template.textContent = `Template ${i}`;

        const templateBody = templateClone.querySelector('.template-item-body');

        
        for(let j=1; j<= 3; j++){

            const taskClone = dayTempTask.content.cloneNode(true);

            const task = taskClone.querySelector('.template-task-item');
            task.id = `task-${j}-${i}`;

            //TODO: set id based on format task-[template db id]-[template task db id]
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

// Saved Items

export function populateSavedTasks(){
    // Populates the saved tasks section
    // get template
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
        // TODO: replace with JSON data
        // TODO: set id with format: saved-task-[DB id]
        savedTaskItem.textContent = `Saved Task ${i}`; // Set the text content         
        savedTasksContainer.insertBefore(clone, newTask); // Append the cloned item to the container
    }
}
export function populateSavedTemplates(){
    // get templates
    const savedTemplateTemp = document.getElementById('savedTemplate');
    const savedTempTask = document.getElementById('savedTemplateTask');
    const savedTemplateContainer = document.getElementById('saved-templates');
    const newTemplate = document.getElementById('new-template');
    //TODO: API request to get saved templates and template tasks
    for (let i = 1; i<6; i++){
        // MOCK DATA NUMBER OF TASKS
        let numTasks = Math.floor(Math.random() * 10) + 1;

        // clone and modify template content
        const templateClone = savedTemplateTemp.content.cloneNode(true);
        const savedTaskHead = templateClone.querySelector('.saved-template-item-head')

        // add template name and task count
        const templateName = document.createElement('H4');
        templateName.textContent = `Template ${i}`;
        //TODO: Set id based on format: saved-template-[db id]

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
            // TODO: Set Id based on format: saved-temp-task-[db id]
            templateBody.appendChild(taskClone);
        }

        // add template to container
        savedTemplateContainer.insertBefore(templateClone, newTemplate);
    }
}

// Calendar
const monthNames = [
  "January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

export function generateCalendar(month, year, today) {
    // Get the calendar grid and clears content
    const grid = document.getElementById("calendar-grid"); 
    grid.innerHTML = ""; 
    const monthYearHeader = document.getElementById("month-year");
    const firstDay = new Date(year, month, 1).getDay(); // 0 = Sunday
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    
    // Set month and year in header
    if (monthYearHeader) {
        monthYearHeader.textContent = `${monthNames[month].toUpperCase()} ${year}`; 
    }

    var totalCells = 0;
    var sunday = 0;

    // check if first day is not Sunday; Count days from previous Sunday if so
    if (firstDay !== sunday){ 
        const lastMonth = new Date(year, month, 0).getDate();
        sunday= lastMonth-(firstDay-1);
    }
  
    // Add empty cells between Sunday and 1st of the month
    for (let i = 0; i < firstDay; i++) {
        grid.appendChild(createCalendarCell(sunday, [], [], 'disabled'));
        totalCells++;
        sunday++;
    }
  
    // Add normal day cells with tasks and templates
    for (let day = 1; day <= daysInMonth; day++) {
        //TODO: Add API call per day
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

    // check if the last row is not full; add empty cells until saurday if so
    while ((totalCells % 7) != 0){ 
        grid.appendChild(createCalendarCell(days, [], [], 'disabled'));
        totalCells++;
        days++;
    }
}


// Non-export functions
function createCalendarCell(dayNumber, tasks, templates, status = 'normal') {
    // Get template
    const template = document.getElementById('calendarCell');
    
    // Clone and modify the template content
    const clone = template.content.cloneNode(true);
    const dayCell = clone.querySelector('.day-cell'); 
    //TODO: add id based on format: calendar-YYMMDD

    // Add the day number; adds hhghtlight for current date
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
function setDateLabel(date){
    const daysOfWeek = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday"];
    const label = `${daysOfWeek[date.getDay()]}, ${monthNames[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    return label;
}