import {Component} from '@angular/core';

@Component({
    selector: 'app-batalla',
    templateUrl: './batalla.component.html',
    imports: [

    ],
    styleUrls: ['./batalla.component.css']
})
export class BatallaComponent {

  player = {


    name: 'Nidoking',
    hp: 80,
    image: 'assets/nidoking.png',
    moves: [
      { name: 'Poder 1', damage: 10 },
      { name: 'Poder 2', damage: 15 },
      { name: 'Poder 3', damage: 20 },
      { name: 'Poder 4', damage: 30 }
    ],
    team: [
      { name: 'Wigglytuff', icon: 'assets/teams/1.png' },
      { name: 'Golem', icon: 'assets/teams/2.png' },
      { name: 'Gengar', icon: 'assets/teams/3.png' },
      { name: 'Snorlax', icon: 'assets/teams/4.png' },
      { name: 'Lugia', icon: 'assets/teams/5.png' }
    ]
  };

  enemy = {
    name: 'Venusaur',
    hp: 60,
    image: 'assets/venusaur.png'
  };

  selectedMove: any = null;

  useMove(move: any) {
    this.selectedMove = move;
    this.enemy.hp = Math.max(0, this.enemy.hp - move.damage);
  }
}
