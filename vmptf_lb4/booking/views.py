import asyncio
from django.shortcuts import render, redirect, get_object_or_404
from django.db.models import Q
from asgiref.sync import sync_to_async
from .models import Room, Booking, Client, Hotel

async def index(request):
    @sync_to_async
    def get_all_data():
        bookings = list(Booking.objects.all().select_related('client', 'room', 'room__hotel'))
        clients = list(Client.objects.all())
        rooms = list(Room.objects.all().select_related('hotel'))
        return bookings, clients, rooms

    bookings, clients, rooms = await get_all_data()
    return render(request, 'booking/index.html', {
        'bookings': bookings,
        'clients': clients,
        'rooms': rooms
    })

async def async_available_rooms(request):
    check_in = request.GET.get('check_in')
    check_out = request.GET.get('check_out')
    rooms = []

    if check_in and check_out:
        @sync_to_async
        def get_free_rooms():
            booked_rooms = Booking.objects.filter(
                Q(check_in__lt=check_out) & Q(check_out__gt=check_in)
            ).values_list('room', flat=True)
            return list(Room.objects.exclude(id__in=booked_rooms).select_related('hotel'))
        
        rooms = await get_free_rooms()

    return render(request, 'booking/available_rooms.html', {
        'rooms': rooms, 
        'check_in': check_in, 
        'check_out': check_out
    })

async def add_booking(request):
    if request.method == 'POST':
        client_id = request.POST.get('client_id')
        room_id = request.POST.get('room_id')
        check_in = request.POST.get('check_in')
        check_out = request.POST.get('check_out')

        await Booking.objects.acreate(
            client_id=client_id,
            room_id=room_id,
            check_in=check_in,
            check_out=check_out
        )
    return redirect('index')

async def delete_booking(request, booking_id):
    if request.method == 'POST':
        @sync_to_async
        def get_booking():
            return get_object_or_404(Booking, id=booking_id)
        
        booking = await get_booking()
        await booking.adelete()
    return redirect('index')