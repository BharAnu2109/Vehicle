import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VehicleService, Vehicle } from '../../services/vehicle.service';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicles.component.html',
  styleUrl: './vehicles.component.scss'
})
export class VehiclesComponent implements OnInit {
  vehicles: Vehicle[] = [];
  selectedVehicle: Vehicle | null = null;
  isEditing = false;

  newVehicle: Vehicle = {
    vin: '',
    model: '',
    make: '',
    year: new Date().getFullYear(),
    color: '',
    type: '',
    status: 'IN_PRODUCTION'
  };

  constructor(private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.vehicleService.getAllVehicles().subscribe({
      next: (data) => {
        this.vehicles = data;
      },
      error: (err) => {
        console.error('Error loading vehicles:', err);
      }
    });
  }

  createVehicle(): void {
    this.vehicleService.createVehicle(this.newVehicle).subscribe({
      next: () => {
        this.loadVehicles();
        this.resetForm();
      },
      error: (err) => {
        console.error('Error creating vehicle:', err);
      }
    });
  }

  editVehicle(vehicle: Vehicle): void {
    this.selectedVehicle = { ...vehicle };
    this.isEditing = true;
  }

  updateVehicle(): void {
    if (this.selectedVehicle && this.selectedVehicle.id) {
      this.vehicleService.updateVehicle(this.selectedVehicle.id, this.selectedVehicle).subscribe({
        next: () => {
          this.loadVehicles();
          this.cancelEdit();
        },
        error: (err) => {
          console.error('Error updating vehicle:', err);
        }
      });
    }
  }

  deleteVehicle(id: number): void {
    if (confirm('Are you sure you want to delete this vehicle?')) {
      this.vehicleService.deleteVehicle(id).subscribe({
        next: () => {
          this.loadVehicles();
        },
        error: (err) => {
          console.error('Error deleting vehicle:', err);
        }
      });
    }
  }

  cancelEdit(): void {
    this.selectedVehicle = null;
    this.isEditing = false;
  }

  resetForm(): void {
    this.newVehicle = {
      vin: '',
      model: '',
      make: '',
      year: new Date().getFullYear(),
      color: '',
      type: '',
      status: 'IN_PRODUCTION'
    };
  }
}
