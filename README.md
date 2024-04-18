\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{amsmath}
\usepackage{amsfonts}
\usepackage{graphicx}

\title{Marvel: Ultimate War}
\author{German University in Cairo \\ Media Engineering and Technology \\ Prof. Dr. Slim Abdennadher \\ Computer Programming Lab, Spring 2022}
\date{}

\begin{document}

\maketitle

\section*{Game Description}

This document provides a detailed description of the game elements and gameplay. It does not contain any implementation or coding details.

\subsection*{Intro}

Marvel: Ultimate War is a 2 player battle game. Each player picks 3 champions to form his team and fight the other player’s team. The players take turns to fight the other player’s champions. The turns will keep going back and forth until a player is able to defeat all of the other player’s champions which will make him the winner of the battle.

During the battle, each player will use his champions to attack the opponent champions either by using normal attacks or using special attacks/abilities. The battle takes place on a 5x5 grid. Each cell in the grid can either be empty, or contain a champion or obstacle/cover. At the beginning of the battle, each team will stand at one of the sides/edges of the grid as a starting position.

\subsection*{Champions}

Champions are the fighters that each player will form his team from. Each champion will have a certain type which influences how the champion deals damage to other types as well as how much damage it will receive from them. The available types are:

\begin{itemize}
    \item Heroes: they deal extra damage when attacking villains.
    \item Villains: they deal extra damage when attacking heroes.
    \item Anti-Heroes: when being attacked or attacking a hero or villain, the antihero will always act as the opposite type. If attacking an antihero, damage is calculated normally.
\end{itemize}

The available champions along with their corresponding type:

\begin{table}[h!]
\centering
\begin{tabular}{|c|c|c|}
\hline
Champion & Type & Champion & Type & Champion & Type \\
\hline
Captain America & Hero & Deadpool & Anti-Hero & Dr Strange & Hero \\
Electro & Villain & Ghost Rider & Anti-Hero & Hela & Villain \\
Hulk & Hero & Iceman & Hero & Ironman & Hero \\
Loki & Villain & Quicksilver & Villain & Spiderman & Hero \\
Thor & Hero & Venom & Anti-Hero & Yellow Jacket & Villain \\
\hline
\end{tabular}
\end{table}

Each champion has the following attributes and characteristics:

\begin{itemize}
    \item Health points: Represents the life of the champion.
    \item Mana: a resource that a champion uses to use his abilities.
    \item Normal attack damage: The amount of damage that the champion will inflict upon the attacked champion while using a normal attack.
    \item Normal attack range: The maximum number of cells that the attacker’s normal attack can reach the attacked champion within.
    \item Speed: Determines how fast the champion is.
    \item Condition: Represents the current ability/inability of the champion to act.
    \item Actions per turn: A number representing how many actions a player can do with the champion during each of his turns.
\end{itemize}

Possible actions that can be done by a champion during his turn:

\begin{itemize}
    \item Move to an empty cell.
    \item Do a normal attack.
    \item Cast an ability.
    \item Use Leader Ability (only if champion is the player’s chosen leader).
\end{itemize}

\subsection*{Abilities}

These are special attacks that a champion can use. They are categorized under the following categories:

\begin{itemize}
    \item Damaging abilities
    \item Healing abilities
    \item Effect abilities
\end{itemize}

Abilities have different targets and ranges. Some abilities are single target abilities which affect only a single champion per use, or can affect any champion standing in a certain area (area of effect). Finally, some abilities can affect all friendly or opposing champions.

\subsection*{Leader Abilities}

At the beginning of the battle, each player promotes one of his champions to be the leader of his team. The leader will then receive a special ability based on his type that can be used only once per battle.

\subsection*{Gameplay Flow}

Each player will select his three champions to form his team. The champions will take turns based on their speed. The champion with the highest speed will begin acting first followed by the champion with the second highest speed and so on. When the turn goes to a champion, the player controlling the champion can use him to carry out any action as long as the champion has enough action points needed for this action and also enough mana in case of using any of his abilities. After that, the champion can end his turn and the turn will go to the next champion.

The turns will keep passing over the living champions till a player is able to defeat all of the three champions of the opponent player. In this case, the game ends and the player with the living champion will be declared the winner.

\end{document}
