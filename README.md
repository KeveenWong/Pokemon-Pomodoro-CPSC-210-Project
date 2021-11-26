# Pomodoro: The Pokémon Collecting Game! 

## A fun take on the **[Pomodoro Technique](https://en.wikipedia.org/wiki/Pomodoro_Technique)**

#### So, what does this application *do?*

> This application utilizes the famous Pomodoro Technique and turns it into something fun rather than a mere timer.
> Players can start the Pomodoro timer and begin working, with options to pause the timer, end the timer, and more.
> Once the task is complete, they will receive a random Pokémon and may exit the timer in order to keep or release
> them, as well as give them a name. These kept Pokémon will then be added to your collection, in which you can view at
> any time.
> All of these features allow for a fun experience while motivating the player to complete real life tasks in order to expand 
> their collection.
> > *Gotta Catch 'Em All!*

#### What audience does this application target? 

> This application is meant for people who struggle to be productive because working/studying is *boring*. No one wants
> to complete all their work just for there to be no reward at the end. This app attempts to make the user feel rewarded
> when they complete tasks so that they can feel motivated to do more work. This is known as **positive reinforcement,** 
> a popular concept in psychology. While this application may resonate more strongly with individuals familiar with the 
> Pokémon franchise, it can be picked up and enjoyed by anyone. 

#### Why am I interested in making this application?

> Throughout my entire life, I have played video games as a hobby. I love the fact that I can boot up a game and get 
> immersed in a virtual world, alleviating the stress of my everyday life. Video games have always been very therapeutic 
> to me, and is the reason why myself and many others love playing games. Pokémon has always been one of my favourite
> games and hold some of my best childhood memories. Furthermore, I've always been very interested 
> in making my own game. I am hoping that creating this application can give me insight on some design aspects required 
> to make a simple interactive game, while also being a project that genuinely seems fun to create. 

## User Stories

> #### As a user, I want to be able to...
> - Start a Pomodoro Timer
> - Pause a Pomodoro Timer
> - Unpause a Pomodoro timer
> - Reset a Pomodoro Timer
> - Choose which Pokémon I want to add to my collection
> - Check my collection of Pokémon
> - Save my Pokémon collection to file
> - Load my Pokémon collection to file
>

#### Phase 4: Task 2
> Fri Nov 26 11:28:09 PST 2021. \
> Added Pokemon to Collection. \
> Fri Nov 26 11:28:09 PST 2021 \
> Added Pokemon to Collection. \
> Fri Nov 26 11:28:11 PST 2021 \
> Saved Pokemon to JSON. \
> Fri Nov 26 11:28:11 PST 2021 \
> Saved Pokemon to JSON.
>

#### Phase 4: Task 3
> ####If I had more time to work on this project, I would definitely improve on: 
> 
> - My cohesion in my PomodoroPokemonGUI class. Currently, I have multiple classes that represent different GUI menus 
> within my PomodoroPokemonGUI class (e.g. TimerMenu, SelectionMenu, CollectionPanel), which I should probably have 
> separated into different class objects in my ui package. I also have CustomOutputStream, which may have been better to 
> also make a new class object for. 
> 
> - Simplifying my Pokemon collecting process by entirely eliminating the TempCollection class. I believe that there
> is a simpler way to keep track of which Pokemon are added or removed to my collection, and that the usage of 
> TempCollection is a very roundabout way of doing so currently. 
> 
> - Make my TimerMenu less console-reliant and more graphical-reliant. Right now, it just creates a JTextArea of the 
> console output. I would like to refactor it to display numbers as a JLabel on the GUI, but in order to do so, I must 
> reduce the amount of coupling that would occur. I had initially attempted to do it this way, but found that coupling
> was too much of an issue. 


*© 2021 Pokémon. © 1995–2021 Nintendo/Creatures Inc./GAME FREAK inc. Pokémon, Pokémon character names, Nintendo Switch, 
Nintendo 3DS, Nintendo DS, Wii, Wii U, and WiiWare are trademarks of Nintendo.*