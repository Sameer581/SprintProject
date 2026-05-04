import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupRequestListComponent } from './group-request-list.component';

describe('GroupRequestListComponent', () => {
  let component: GroupRequestListComponent;
  let fixture: ComponentFixture<GroupRequestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GroupRequestListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GroupRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
