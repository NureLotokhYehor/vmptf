from django.contrib import admin
from .models import Client, Hotel, Room, Service, Booking

admin.site.register(Client)
admin.site.register(Hotel)
admin.site.register(Room)
admin.site.register(Service)
admin.site.register(Booking)