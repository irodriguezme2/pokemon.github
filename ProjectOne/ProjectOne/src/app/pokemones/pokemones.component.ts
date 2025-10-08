import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-pokemones',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './pokemones.component.html',
  styleUrls: ['./pokemones.component.css']
})
export class PokemonesComponent implements OnInit {

  pokemons: any[] = [];
  filteredPokemons: any[] = [];
  offset = 0;
  limit = 200; // cantidad por carga

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPokemons(); // carga inicial
  }

  loadPokemons(): void {
    const apiUrl = `https://pokeapi.co/api/v2/pokemon?limit=${this.limit}&offset=${this.offset}`;

    this.http.get<any>(apiUrl).subscribe(response => {
      const results = response.results;

      results.forEach((pokemon: any) => {
        this.http.get<any>(pokemon.url).subscribe(data => {
          const tipos = data.types.map((t: any) => t.type.name);
          const id = data.id.toString().padStart(3, '0');

          this.pokemons.push({
            id,
            name: data.name,
            image: data.sprites.other['official-artwork'].front_default,
            types: tipos,
            height: (data.height / 10).toFixed(1), // ✅ convertir decímetros → metros
            weight: (data.weight / 10).toFixed(1),
            abilities: data.abilities.map((a: any) => a.ability.name), // ✅ habilidades
            stats: data.stats.map((s: any) => ({                      // ✅ estadísticas
              name: s.stat.name,
              value: s.base_stat
            }))
          });

          // Mostrar todos por defecto
          this.filteredPokemons = [...this.pokemons];
        });
      });
    });
  }


  // 🔹 Cargar más Pokémon
  loadMore(): void {
    this.offset += this.limit;
    this.loadPokemons();
  }

  // 🔹 Filtrar por tipo
  filterByType(type: string): void {
    if (type === 'ver-todos') {
      this.filteredPokemons = [...this.pokemons];
    } else {
      this.filteredPokemons = this.pokemons.filter(p =>
        p.types.includes(type)
      );
    }
  }
}
