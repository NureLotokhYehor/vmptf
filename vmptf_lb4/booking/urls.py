from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('available/', views.async_available_rooms, name='available_rooms'),
    path('add/', views.add_booking, name='add_booking'),
    path('delete/<int:booking_id>/', views.delete_booking, name='delete_booking'),
]