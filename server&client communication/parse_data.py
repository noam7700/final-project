def parse_data(data):  # data fromat: first line- query description. next lines: client details(&basket data if created)
    query = data.readline()
    username = data.readline()
    pas = data.readline()
    c_details = (username, pas)
    if query == 'registeration':
        clients_data.append((c_details, []))
        return "registeration completed"
    if query == 'log-in':
       ''' if clients_data.get(c_details) == None:
            return "details not exist in the system";
        else:
            file = open('ProductsTextData.txt')
            products_details = file.read()
            return products_details
        # transfer productsDetails file'''

    if query == 'bring saved baskets':
       ''' c_unit = clients_data.get(c_details)
        (c_det, bask_details) = c_unit
        return bask_details '''

    if query == 'add basket to saved':
        '''c_unit = clients_data.get(c_details)
        (c_det, bask_details) = c_unit
        bask_details.append(data.read())
        return "succesfully added new basket"'''

    return "unrecognized message"
